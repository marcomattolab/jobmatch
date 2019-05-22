import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { AfterViewInit, Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { FormControl, NgForm } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbTypeaheadSelectItemEvent } from '@ng-bootstrap/ng-bootstrap';
import { AccountService } from 'app/core';
import { disableFooter, enableFooter, ITEMS_PER_PAGE } from 'app/shared';
import { Country, ICandidate, SearchCandidate, SelectableCandidate } from 'app/shared/model/candidate.model';
import { ISkillTag, SkillType } from 'app/shared/model/skill-tag.model';
import { ISkill } from 'app/shared/model/skill.model';
import { SearchFilterParamsService } from 'app/shared/search-filter-params/search-filter-params.service';
import { UserSessionStorageService } from 'app/shared/user-session-storage/user-session-storage.service';
import { JhiAlertService, JhiDataUtils, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { Observable, of, Subscription } from 'rxjs';
import { catchError, debounceTime, map, switchMap } from 'rxjs/operators';
import { isNullOrUndefined } from 'util';
import { SkillTagService } from '../skill-tag/skill-tag.service';
import { CandidateService } from './candidate.service';

@Component( {
    selector: 'jhi-candidate-profile-lis',
    templateUrl: './candidate-profile-list.component.html'
} )
export class CandidateProfileListComponent implements OnInit, OnDestroy, AfterViewInit {
    currentAccount: any;
    candidates: SelectableCandidate[];
    skillTags: ISkillTag[];
    error: any;
    success: any;
    eventSubscriber: Subscription;
    routeData: any;
    links: any;
    totalItems: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;

    // select nazioni
    countries = Object.keys( Country );
    showTable = false;

    allSelected = false;
    selectMode = false;
    selectedCandidates: SelectableCandidate[];
    alreadySelectedCandidates: SelectableCandidate[];

    // filtri ricerca
    filters: SearchCandidate = {};
    skillTagNameTemp: any;
    @ViewChild( 'searchForm' )
    searchForm: NgForm;

    constructor(
        protected candidateService: CandidateService,
        protected parseLinks: JhiParseLinks,
        protected jhiAlertService: JhiAlertService,
        protected accountService: AccountService,
        protected activatedRoute: ActivatedRoute,
        protected dataUtils: JhiDataUtils,
        protected router: Router,
        protected eventManager: JhiEventManager,
        protected skillTagService: SkillTagService,
        private searchFilterParamsService: SearchFilterParamsService,
        protected userSessionStorageService: UserSessionStorageService
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe( data => {
            this.page = data.pagingParams.page;
            this.previousPage = data.pagingParams.page;
            this.reverse = data.pagingParams.ascending;
            this.predicate = data.pagingParams.predicate;
            this.selectMode = data.selectMode;
            this.showTable = this.selectMode;
        } );
    }

    search() {
        const queryParams: any = {
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort()
        };
        if ( this.filters ) {
            if ( this.filters.firstName ) {
                queryParams['firstName.contains'] = this.filters.firstName;
            }
            if ( this.filters.lastName ) {
                queryParams['lastName.contains'] = this.filters.lastName;
            }
            //            if ( this.filters.skillTagName ) {
            //                queryParams['skillTagName.contains'] = this.filters.skillTagName;
            //            }
            if ( this.filters.skillsTag && this.filters.skillsTag.length > 0 ) {
                const skillTagIds = this.filters.skillsTag.map( skillTag => skillTag.id );
                queryParams['skillTagId.in'] = skillTagIds;
                this.searchForm.form.controls['skillTags'] = new FormControl( this.filters.skillsTag );
            }
            if ( this.filters.currentJobCity ) {
                queryParams['currentCity.contains'] = this.filters.currentJobCity;
            }
            if ( this.filters.currentJobCountry ) {
                queryParams['currentCountry.equals'] = this.filters.currentJobCountry;
            }
            if ( this.filters.currentJobTitle ) {
                queryParams['currentJobTitle.contains'] = this.filters.currentJobTitle;
            }
        }

        this.searchFilterParamsService.storePathFormParams( this.router.url, this.searchForm.form );

        this.candidateService
            .query( queryParams )
            .subscribe(
            ( res: HttpResponse<ICandidate[]> ) => this.paginateCandidates( res.body, res.headers ),
            ( res: HttpErrorResponse ) => this.onError( res.message )
            );
    }

    loadAll() {
        this.search();
    }

    loadPage( page: number ) {
        if ( page !== this.previousPage ) {
            this.previousPage = page;
            this.transition();
        }
    }

    transition() {
        this.refreshSelectedCandidates();
        this.navigateToMyself();
        this.search();
    }

    clear() {
        this.page = 0;
        this.navigateToMyself();
        this.search();
    }

    navigateToMyself() {
        if ( this.selectMode ) {
            this.router.navigate( ['candidate', 'select'], {
                queryParams: {
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.predicate + ',' + ( this.reverse ? 'asc' : 'desc' )
                }
            } );
        } else {
            this.router.navigate( ['candidate'], {
                queryParams: {
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.predicate + ',' + ( this.reverse ? 'asc' : 'desc' )
                }
            } );
        }
    }

    reloadFlagSelectMode() {
        if ( this.selectMode ) {
            for ( const cand of this.selectedCandidates ) {
                const foundCand = this.candidates.find( c => c.id === cand.id );
                if ( foundCand ) {
                    foundCand.selected = true;
                }
            }
        }
    }

    selectAll( $event ) {
        for ( const elPage of this.candidates ) {
            const alreadySelectedPreviously = this.alreadySelectedPreviously( elPage.id );
            if ( alreadySelectedPreviously ) {
                continue;
            }

            elPage.selected = $event.target.checked;
            const alreadySelectedInPage = this.selectedCandidates.find( x => x.id === elPage.id );
            if ( elPage.selected ) {
                if ( isNullOrUndefined( alreadySelectedInPage ) ) {
                    this.selectedCandidates.push( elPage );
                }
            } else {
                if ( !isNullOrUndefined( alreadySelectedInPage ) ) {
                    this.selectedCandidates.removeUniqueIf( x => x.id === elPage.id );
                }
            }
        }
    }

    refreshSelectedCandidates() {
        if ( this.selectMode ) {
            const selected = this.candidates.filter( x => x.selected );
            for ( const cand of selected ) {
                this.selectedCandidates.pushIf( cand, c => c.id !== cand.id );
            }
        }
    }

    loadAlreadySelectedCandidates() {
        if ( this.selectMode ) {
            this.alreadySelectedCandidates = this.userSessionStorageService.retrieveCandidatesSelected();
        }
    }

    ngOnInit() {
        this.accountService.identity().then( account => {
            this.currentAccount = account;
        } );
        this.selectedCandidates = [];
        this.loadAlreadySelectedCandidates();
        this.registerChangeInCandidates();
        enableFooter(document);
    }

    ngAfterViewInit() {
        setTimeout(() => {
            this.initFilters();
            this.loadAll();
        }, 0 );
    }

    alreadySelectedPreviously( candId: number ): boolean {
        const found = this.alreadySelectedCandidates.find( x => x.id === candId );
        return !isNullOrUndefined( found );
    }

    ngOnDestroy() {
        this.eventManager.destroy( this.eventSubscriber );
        disableFooter(document);
    }

    trackId( index: number, item: ICandidate ) {
        return item.id;
    }

    byteSize( field ) {
        return this.dataUtils.byteSize( field );
    }

    openFile( contentType, field ) {
        return this.dataUtils.openFile( contentType, field );
    }

    registerChangeInCandidates() {
        this.eventSubscriber = this.eventManager.subscribe( 'candidateListModification', response => this.loadAll() );
    }

    sort() {
        const result = [this.predicate + ',' + ( this.reverse ? 'asc' : 'desc' )];
        if ( this.predicate !== 'id' ) {
            result.push( 'id' );
        }
        return result;
    }

    protected paginateCandidates( data: ICandidate[], headers: HttpHeaders ) {
        this.links = this.parseLinks.parse( headers.get( 'link' ) );
        this.totalItems = parseInt( headers.get( 'X-Total-Count' ), 10 );
        this.candidates = data;
        this.reloadFlagSelectMode();
    }

    protected onError( errorMessage: string ) {
        this.jhiAlertService.error( errorMessage, null, null );
    }

    filterSkill( skills: ISkill[] ) {
        return skills.filter( s => s.skillTagType !== SkillType.LANGUAGE );
    }

    searchSkills = ( text$: Observable<string> ) =>
        text$.pipe(
            debounceTime( 200 ),
            switchMap( term => ( ( this.filters.skillTagName = term ) === '' ) ? of( [] ) :
                this.skillTagService.query( { 'name.contains': term, 'type.equals': SkillType.OTHER, 'size': 6, 'sort': ['name,asc'] } ).pipe(
                    map(( skillTags: HttpResponse<ISkillTag[]> ) => ( this.skillTags = skillTags.body ).map( skill => this.skillTagFormatter( skill ) ) ),
                    catchError(() => of( this.skillTags = [] ) )
                )
            ),
        );

    skillTagFormatter = ( skillTag: ISkillTag ) => !isNullOrUndefined( skillTag ) ? skillTag.name : '';

    initFilters() {
        this.filters = new SearchCandidate();
        this.searchFilterParamsService.patchFormWithParams( this.router.url, this.searchForm.form );
        if ( this.searchForm.form.controls && this.searchForm.form.controls.skillTags ) {
            this.filters.skillsTag = this.searchForm.form.controls.skillTags.value;
        }
    }

    resetFilters() {
        this.filters = new SearchCandidate();
        this.searchFilterParamsService.clearPathFormParams( this.router.url );
    }

    formSubmitted() {
        this.selectedCandidates = [];
        this.search();
    }

    onSelectedModeSubmit() {
        this.refreshSelectedCandidates();
        this.userSessionStorageService.storeLastCandidatesSelected( this.selectedCandidates );
        this.back();
    }

    onSelectSkillTag( ngbTypeaheadSelectItem: NgbTypeaheadSelectItemEvent ) {
        ngbTypeaheadSelectItem.preventDefault();

        const skillTagFound = this.skillTags.find( skillTag =>
            this.skillTagFormatter( skillTag ) === ngbTypeaheadSelectItem.item );

        if ( skillTagFound ) {
            this.skillTagNameTemp = '';
            if ( this.filters.skillsTag.indexOf( skillTagFound ) === -1 ) {
                this.filters.skillsTag.push( skillTagFound );
            }
        }

    }

    removeSkillTag( skillTag: ISkillTag ) {
        const index = this.filters.skillsTag.indexOf( skillTag, 0 );
        if ( index > -1 ) {
            this.filters.skillsTag.splice( index, 1 );
        }
    }

    back() {
        window.history.back();
    }

}
