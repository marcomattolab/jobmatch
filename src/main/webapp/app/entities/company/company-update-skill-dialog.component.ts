import { HttpResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ICompanySkill, CompanySkill } from 'app/shared/model/company-skill.model';
import { ISkillTag, SkillType } from 'app/shared/model/skill-tag.model';
import { JhiEventManager } from 'ng-jhipster';
import { Observable, of } from 'rxjs';
import { catchError, debounceTime, map, switchMap } from 'rxjs/operators';
import { CompanySkillService } from '../company-skill/company-skill.service';
import { SkillTagService } from '../skill-tag';
import { isValidId, pipeToResponse } from 'app/shared';
import { isNullOrUndefined } from 'util';

@Component( {
    selector: 'jhi-company-update-skill-dialog',
    templateUrl: './company-update-skill-dialog.component.html'
} )
export class CompanyUpdateSkillDialogComponent implements OnInit {
    companyId: number;
    skillTags: ISkillTag[];
    currentSkill: ICompanySkill;
    companySkills: ICompanySkill[];
    skillType: SkillType;
    needReload = true;
    isSaving = false;
    alreadyHaveSkill = false;
    currentSkillTag: string;

    constructor(
        protected companySkillService: CompanySkillService,
        protected skillTagService: SkillTagService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) { }

    ngOnInit() {
        if ( isValidId( this.companyId ) ) {
            pipeToResponse( this.companySkillService.query( { 'companyId.equals': this.companyId } ) ).subscribe(
                res => this.companySkills = res
            );
        }

        if ( this.currentSkill ) {
            this.currentSkillTag = this.currentSkill.skillTagName;
        }

        this.currentSkill.skillTagType = this.skillType;
    }

    confirm() {
        this.isSaving = true;
        if ( this.skillTags ) {
            const lowerCaseSkill = this.currentSkillTag.toLowerCase();
            const filtered = this.skillTags.filter( tag => tag.name.toLowerCase() === lowerCaseSkill );
            if ( filtered.length > 0 ) {
                this.alreadyHaveSkill = !isNullOrUndefined( this.companySkills.find( skill => skill.skillTagId === filtered[0].id ) );
                if ( this.alreadyHaveSkill ) {
                    this.isSaving = false;
                    return;
                }

                this.currentSkill.skillTagId = filtered[0].id;
            } else {
                this.loadNewTag();
            }
        } else {
            if ( this.checkIfNewSkill() || !this.checkIfTagNameEqualToPrevious() ) {
                this.loadNewTag();
            }
        }

        this.createOrUpdateSkill();
    }

    checkIfNewSkill() {
        return !isValidId( this.currentSkill.id );
    }

    checkIfTagNameEqualToPrevious() {
        return this.currentSkill.skillTagName.toLowerCase() === this.currentSkillTag.toLowerCase();
    }

    loadNewTag() {
        this.currentSkill.skillTagId = null;
        this.currentSkill.skillTagName = this.currentSkillTag;
    }

    createOrUpdateSkill() {
        if ( this.currentSkill.id ) {
            this.companySkillService.update( this.currentSkill ).subscribe(
                res => {
                    this.needReload = true;
                    this.onClose();
                },
                err => {
                    this.isSaving = false;
                }
            );
        } else {
            this.currentSkill.companyId = this.companyId;
            this.companySkillService.create( this.currentSkill ).subscribe(
                res => {
                    this.needReload = true;
                    this.onClose();
                },
                err => {
                    this.isSaving = false;
                }
            );
        }
    }

    searchSkills = ( text$: Observable<string> ) =>
        text$.pipe(
            debounceTime( 200 ),
            switchMap( term => ( ( this.currentSkill.skillTagName = term ) === '' ) ? of( [] ) :
                this.skillTagService.query( { 'name.contains': term, 'type.equals': this.skillType, 'size': 6, 'sort': ['name,asc'] } ).pipe(
                    map(( skillTags: HttpResponse<ISkillTag[]> ) => ( this.skillTags = skillTags.body ).map( skill => this.skillTagFormatter( skill ) ) ),
                    catchError(() => of( this.skillTags = [] ) )
                )
            ),
        );

    skillTagFormatter = ( skillTag: ISkillTag ) => skillTag ? skillTag.name : '';

    onClose() {
        if ( this.needReload ) {
            this.eventManager.broadcast( {
                name: 'companySkillsModified',
                content: 'Candidate s educations has been modified'
            } );
            this.activeModal.dismiss( true );
        }
    }
}

/*
@Component( {
    selector: 'jhi-company-update-skill-popup',
    template: ''
} )
export class CompanyUpdateSkillPopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor( protected activatedRoute: ActivatedRoute, protected router: Router,
        protected modalService: NgbModal, protected skillTagService: SkillTagService,
        protected companySkillService: CompanySkillService ) { }

    ngOnInit() {
        this.activatedRoute.data.subscribe(( { skill } ) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open( CompanyUpdateSkillDialogComponent as Component, { size: 'lg', backdrop: 'static' } );
                this.ngbModalRef.componentInstance.currentSkill = skill;
                this.ngbModalRef.componentInstance.companyId = this.activatedRoute.snapshot.params['id'];
                this.ngbModalRef.componentInstance.skillType = this.activatedRoute.snapshot.data['skillType'];

                if (isValidId(this.ngbModalRef.componentInstance.companyId)) {
                    pipeToResponse(this.companySkillService.query({'companyId.equals': this.ngbModalRef.componentInstance.companyId})).subscribe(
                        res => this.ngbModalRef.componentInstance.companySkills = res
                    );
                }

                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate( ['/company', { outlets: { popup: null } }] );
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate( ['/company', { outlets: { popup: null } }] );
                        this.ngbModalRef = null;
                    }
                );
            }, 0 );
        } );
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}*/
