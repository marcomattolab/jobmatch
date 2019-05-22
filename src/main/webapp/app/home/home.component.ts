import { AfterViewInit, Component, OnDestroy, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Account, AccountService, LoginModalService } from 'app/core';
import { AppliedJobService } from 'app/entities/applied-job';
import { CandidateService } from 'app/entities/candidate';
import { CompanyService } from 'app/entities/company/company.service';
import { JobOfferService } from 'app/entities/job-offer/job-offer.service';
import { disableFooter, enableFooter } from 'app/shared';
import { AuthoritiesConstants } from 'app/shared/constants/authorities.constants';
import { AppiedJobStatus, AppliedJobItem } from 'app/shared/model/applied-job.model';
import { ICandidate } from 'app/shared/model/candidate.model';
import { CompanyInfo, ICompany } from 'app/shared/model/company.model';
import { IJobOffer } from 'app/shared/model/job-offer.model';
import { SkillType } from 'app/shared/model/skill-tag.model';
import { ISkill } from 'app/shared/model/skill.model';
import { SearchFilterParamsService } from 'app/shared/search-filter-params/search-filter-params.service';
import { pipeToResponse } from 'app/shared/util/request-util';
import * as moment from 'moment';
import { JhiEventManager } from 'ng-jhipster';

@Component( {
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: ['home.scss']
} )
export class HomeComponent implements OnInit, OnDestroy, AfterViewInit {

    account: Account;
    modalRef: NgbModalRef;

    // FOR PROMOTER USER
    // numero di aziende presenti su JobMatch
    allCompaniesRegisteredCount: number;
    // numero di offerte di Lavoro attive presenti su JobMatch.
    allJobOffersCount: number;
    // numero di candidati presenti su JobMatch
    allCandidatesRegisteredCount: number;
    // allApplymentsCount: number;

    // FOR CANDIDATE USER
    // Offerte di lavoro suggerite (per affinita')
    suggestedJobOffers: IJobOffer[];
    // Aziende suggerite (per affinita')
    suggestedCompanies: ICompany[];
    // Numero Offerte di lavoro Attive su JobMatch
    allActiveJobOffersCount: number;
    // numero di Offerte di lavoro Attive inserite di recente (ultima settimana) su JobMatch
    lastWeekActiveJobOffersCount: number;
    // numero delle mie candidature
    myApplymentsCount: number;

    // FOR COMPANY USER
    company: CompanyInfo;
    // candidati suggeriti (per affinita')
    suggestedCandidates: ICandidate[];
    // ultime 6 candidature ricevute (in stato NEW)
    lastestAppliedJobs: AppliedJobItem[];
    // Le mie Offerte di lavoro Attive
    activeJobOffersCount: number;
    // Le mie nuove Offerte di lavoro (ultima settimana)
    newJobOffersCount: number;
    // numero di Candidature ricevute recentemente (utlima settimana)
    newApplicationsCount: number;

    // FASE 3 PLACEHOLDER
    lastestNews = [];

    constructor(
        private accountService: AccountService,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager,
        private jobOfferService: JobOfferService,
        private companyService: CompanyService,
        private candidateService: CandidateService,
        private appliedJobService: AppliedJobService,
        private searchFilterParamsService: SearchFilterParamsService
    ) { }

    ngOnInit() {
        this.accountService.identity().then(( account: Account ) => {
            this.account = account;
        } );
        this.registerAuthenticationSuccess();
        this.searchFilterParamsService.reset();
        this.mockNews();
        enableFooter(document);
    }

    ngAfterViewInit() {
        this.reloadHomePageInfo();
    }

    ngOnDestroy() {
        disableFooter(document);
    }

    reloadHomePageInfo() {
        setTimeout(() => {
            this.resetHomePageInfo();
            this.loadIfCandidate();
            this.loadIfCompany();
            this.loadIfSponsoringInstitution();
        }, 0 );
    }

    resetHomePageInfo() {
        // Candidate
        this.suggestedJobOffers = null;
        this.suggestedCompanies = null;
        this.allActiveJobOffersCount = null;
        this.lastWeekActiveJobOffersCount = null;
        this.myApplymentsCount = null;

        // Company
        this.suggestedCandidates = null;
        this.lastestAppliedJobs = null;
        this.newApplicationsCount = null;
        this.activeJobOffersCount = null;
        this.newJobOffersCount = null;

        // Sponsoring institution
        this.allCompaniesRegisteredCount = null;
        this.allJobOffersCount = null;
        this.allCandidatesRegisteredCount = null;
        // this.allApplymentsCount = 0;
    }

    loadIfCandidate() {
        if ( this.accountService.isCurrentRoleCandidate() ) {
            // Offerte di lavoro suggerite (per affinita')
            pipeToResponse( this.jobOfferService.findSuggestedJobOffers( this.accountService.getCurrentRoleId() ) ).subscribe(
                ( res: IJobOffer[] ) => this.suggestedJobOffers = res
            );
            // Aziende suggerite (per affinita')
            pipeToResponse( this.companyService.findSuggestedCompanies( this.accountService.getCurrentRoleId() ) ).subscribe(
                ( res: ICompany[] ) => this.suggestedCompanies = res
            );
            // numero Offerte di lavoro Attive su JobMatch
            this.jobOfferService.countAllActiveJobOffers().subscribe(
                ( allCountActiveJob: number ) => this.allActiveJobOffersCount = allCountActiveJob );
            // numero di Offerte di lavoro Attive inserite di recente (ultima settimana) su JobMatch
            this.jobOfferService.countAllWeeklyActiveJobOffers().subscribe(
                ( allWeeklyCountActiveJob: number ) => this.lastWeekActiveJobOffersCount = allWeeklyCountActiveJob );
            // numero delle mie candidature
            this.appliedJobService.countApplymentsCandidate( this.accountService.getCurrentRoleId() ).subscribe(
                ( countAppliedJob: number ) => this.myApplymentsCount = countAppliedJob );
        }
    }

    loadIfSponsoringInstitution() {
        if ( this.accountService.isCurrentRoleSponsoringInstitution() ) {
            this.companyService.retriveCurrentUserCompanyInfo().subscribe(
                ( res: CompanyInfo ) => this.company = res );
            // numero di aziende presenti su JobMatch
            this.companyService.countAll().subscribe(
                ( countAllCompaniesEnabled: number ) => this.allCompaniesRegisteredCount = countAllCompaniesEnabled
            );
            // numero di offerte di Lavoro attive presenti su JobMatch.
            this.jobOfferService.countAllActiveJobOffers().subscribe(
                ( countAllNonDraftJobOffer: number ) => this.allJobOffersCount = countAllNonDraftJobOffer
            );
            //            this.jobOfferService.countAllNonDraftJobOffers().subscribe(
            //                ( countAllNonDraftJobOffer: number ) => this.allJobOffersCount = countAllNonDraftJobOffer
            //            )
            // ;
            // numero di candidati presenti su JobMatch
            this.candidateService.countAll().subscribe(
                ( countAllCandidatesEnabled: number ) => this.allCandidatesRegisteredCount = countAllCandidatesEnabled
            );
            //            const paramsAppliedJobs = { 'searchMode': true };
            //            this.appliedJobService.countAllApplyments( paramsAppliedJobs ).subscribe(
            //                ( countAllApplyments: number ) => this.allApplymentsCount = countAllApplyments
            //            );
        }
    }

    loadIfCompany() {
        if ( this.accountService.isCurrentRoleCompany() ) {
            this.companyService.retriveCurrentUserCompanyInfo().subscribe(
                ( res: CompanyInfo ) => this.company = res
            );

            const companyId = this.accountService.getCurrentRoleId();
            // candidati suggeriti (per affinita')
            this.candidateService.findSuggestedCandidatesByCompanyId( companyId ).subscribe(
                ( res: ICandidate[] ) => this.suggestedCandidates = res
            );
            // ultime 6 candidature ricevute (in stato NEW)
            const paramsAppliedJobs = { 'companyId.equals': companyId, 'appiedJobStatus.equals': AppiedJobStatus.NEW, 'size': 6, 'sort': ['createdDate,DESC'] };
            pipeToResponse( this.appliedJobService.queryItems( paramsAppliedJobs ) ).subscribe(
                ( res: AppliedJobItem[] ) => this.lastestAppliedJobs = res
            );

            // numero di Candidature ricevute recentemente (utlima settimana)
            this.appliedJobService.countNewApplications( companyId ).subscribe(
                ( countAppliedJob: number ) => this.newApplicationsCount = countAppliedJob );

            // Le mie Offerte di lavoro Attive
            this.jobOfferService.countAllActiveJobOffersByCompany( companyId ).subscribe(
                ( countActiveJob: number ) => this.activeJobOffersCount = countActiveJob );

            // Le mie nuove Offerte di lavoro (ultima settimana)
            this.jobOfferService.countNewJobOffers( companyId ).subscribe(
                ( countJob: number ) => this.newJobOffersCount = countJob );
        }
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe( 'authenticationSuccess', message => {
            this.accountService.identity().then( account => {
                this.account = account;
            } );
        } );
        this.eventManager.subscribe( 'authenticationSuccess', response => this.reloadHomePageInfo() );
    }

    isAuthenticated() {
        return this.accountService.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }

    trackId( index: number, item: any ) {
        return item.id;
    }

    filterSkill( skills: ISkill[] ) {
        return skills.filter( s => s.skillTagType !== SkillType.LANGUAGE );
    }

    setRecentlyJobOffersParams( path: string ) {
        const params: Map<string, any> = new Map;
        let startdate = moment();
        startdate = startdate.subtract( 7, 'days' );
        params.set( 'startDate', startdate );
        this.searchFilterParamsService.storePathParams( '/job-offer/' + path, params );
    }

    isUserAnonymous() {
        return !this.accountService.isAuthenticated() || this.accountService.getCurrentRole() === AuthoritiesConstants.ROLE_USER;
    }

    // FASE 3 PLACEHOLDER
    private mockNews() {
        this.lastestNews.push( { companyName: 'Arancia ICT', title: 'Firma Elettronica Avanzata e Firma Grafometrica', description: 'Vi sarà sicuramente successo di esservi recati in banca e che, in fase di firma di un documento, vi abbiano fornito un pennino con cui scrivere su una tavoletta digitale. Bene, quello che avete fatto non è altro che l’apposizione della “firma grafometrica”. Ma vediamo un po’ cosa vuol dire.' } );
        this.lastestNews.push( { companyName: 'Arancia ICT', title: 'Recruiting Day Unipa – 17/04/2019', description: 'Arancia-ICT organizza un recruiting day alla ricerca di giovani laureati e laureandi in Ingegneria, Scienze Matematiche e Informatiche, Scienze Statistiche che possano operare nell’ambito del Big Data Management (Data & Big Data Engineer e Data & Big Data Analyst) presso le sedi di Palermo e Milano, per un totale di 10 posizioni aperte. Il Data & Big Data Engineer è in grado di implementare pipeline per la gestione dell’intero ciclo di vita dei dati strutturati e non (estrazione, trasformazione e caricamento) in real-time o in batch.' } );
        this.lastestNews.push( { companyName: 'Arancia ICT', title: 'Arancia-ICT in prima linea per la mobilità sostenibile', description: 'Il 2 Aprile 2019 è nata la fondazione ITS InfomobPMO, iniziativa pubblico-privata per la realizzazione di percorsi di formazione superiore nel settore della mobilità sostenibile. Arancia-ICT, partner della fondazione, collaborerà con l’ITIS Vittorio Emanuele III, l’Università degli Studi di Palermo, l’Alta Scuola ARCES e altre aziende partner della fondazione per la realizzazione di percorsi formativi per il conseguimento di un diploma tecnico superiore finalizzato alla costruzione di una carriera professionale fondata su elevate competenze tecniche per promuovere i processi di innovazione.' } );
        this.lastestNews.push( { companyName: 'Arces', title: 'ITS InfomobPMO: Nasce in Sicilia il polo formativo per la mobilità sostenibile', description: 'Nasce in Sicilia la Fondazione “ITS InfomobPMO” per la realizzazione di percorsi di formazione superiore nell’ambito della mobilità sostenibile, settore considerato prioritario per lo sviluppo economico e la competitività del Paese.' } );
        this.lastestNews.push( { companyName: 'Arces', title: 'Sostieni ARCES con il Tuo 5 x mille', description: 'Anche quest’anno è possibile destinare il 5 x mille ad ARCES. Si tratta di sostenere il fondo borse di studio e di mobilità internazionale, insieme a tante altre numerose iniziative messe in campo da ARCES, per favorire il successo scolastico, universitario e professionale dei giovani siciliani. La destinazione del 5 x mille non rappresenta un onere in più per il contribuente, ma è lo Stato che ogni anno si impegna a destinare parte del gettito tributario per queste finalità.' } );
    }
}
