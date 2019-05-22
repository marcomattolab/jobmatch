import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AccountService, LANGUAGES } from 'app/core';
import { FileService } from 'app/shared/file/file.service';
import { GenderType, ICandidate } from 'app/shared/model/candidate.model';
import { DocumentType, IDocument } from 'app/shared/model/document.model';
import { IEducation, EducationType, Education } from 'app/shared/model/education.model';
import { IJobExperience, JobExperience } from 'app/shared/model/job-experience.model';
import { SkillType } from 'app/shared/model/skill-tag.model';
import { ISkill, SkillLevelType, Skill } from 'app/shared/model/skill.model';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils, JhiEventManager, JhiLanguageService } from 'ng-jhipster';
import { Subscription } from 'rxjs';
import { DocumentService } from '../document/document.service';
import { EducationService } from '../education/education.service';
import { JobExperienceService } from '../job-experience/job-experience.service';
import { SkillService } from '../skill/skill.service';
import { CandidateService } from './candidate.service';
import { pipeToResponse } from 'app/shared/util/request-util';
import { MAX_SIZE_PROFILE_IMAGE } from 'app/shared';
import { ModalService } from 'app/shared/modal/modal.service';
import { CandidateUpdateAnagraficaDialogComponent } from './candidate-update-anagrafica-dialog.component';
import { CandidateUpdateJobExperiencesDialogComponent } from './candidate-update-job-experiences-dialog.component';
import { CandidateDeleteJobExperienceDialogComponent } from './candidate-delete-job-experience-dialog.component';
import { CandidateUpdateSkillDialogComponent } from './candidate-update-skill-dialog.component';
import { CandidateDeleteSkillDialogComponent } from './candidate-delete-skill-dialog.component';
import { CandidateUpdateEducationDialogComponent } from './candidate-update-education-dialog.component';
import { CandidateDeleteEducationDialogComponent } from './candidate-delete-education-dialog.component';
import { CandidateUpdateDocumentDialogComponent } from './candidate-update-document-dialog.component';
import { CandidateDeleteDocumentDialogComponent } from './candidate-delete-document-dialog.component';
import { Helper, enableFooter, disableFooter } from 'app/shared/util/helper-util';

@Component( {
    selector: 'jhi-candidate-profile',
    templateUrl: './candidate-profile.component.html'
} )
export class CandidateProfileComponent implements OnInit, OnDestroy {
    candidate: ICandidate;
    jobExperiences: IJobExperience[];
    currentJobExperience: IJobExperience;
    educations: IEducation[];
    certifications: IEducation[];
    otherSkills: ISkill[];
    languageSkills: ISkill[];
    skillLevel = Object.keys( SkillLevelType );
    documents: IDocument[];
    documentsCv: IDocument[];
    currentLanguage: string;
    candidateEventSubscriber: Subscription;
    jobExperiencEventSubscriber: Subscription;
    educationEventSubscriber: Subscription;
    skillEventSubscriber: Subscription;
    documentEventSubscriber: Subscription;
    languageEventSubscriber: Subscription;
    showBackBtn: boolean;

    constructor( protected candidateService: CandidateService,
        protected fileService: FileService,
        protected dataUtils: JhiDataUtils,
        protected jobExperienceService: JobExperienceService,
        protected educationService: EducationService,
        protected languageService: JhiLanguageService,
        protected skillService: SkillService,
        protected activatedRoute: ActivatedRoute,
        protected eventManager: JhiEventManager,
        protected jhiAlertService: JhiAlertService,
        protected accountService: AccountService,
        protected documentService: DocumentService,
        protected modalService: ModalService,
        protected helper: Helper ) { }

    ngOnInit() {
        this.activatedRoute.data.subscribe(( { candidate, showBackBtn } ) => {
            this.candidate = candidate;
            this.showBackBtn = showBackBtn;
            this.reloadJobExperiences();
            this.reloadEducations();
            this.reloadSkills();
            this.reloadDocuments();
            this.changeCurrentLanguage();

            this.candidateEventSubscriber = this.eventManager.subscribe( 'candidateModified', response => this.reloadCandidate() );
            this.jobExperiencEventSubscriber = this.eventManager.subscribe( 'jobExperiencesModified', response => this.reloadJobExperiences() );
            this.educationEventSubscriber = this.eventManager.subscribe( 'educationModified', response => this.reloadEducations() );
            this.skillEventSubscriber = this.eventManager.subscribe( 'skillsModified', response => this.reloadSkills() );
            this.documentEventSubscriber = this.eventManager.subscribe( 'documentsModified', response => this.reloadDocuments() );
            this.languageEventSubscriber = this.eventManager.subscribe( 'languageModified', response => this.changeCurrentLanguage() );
        } );

        enableFooter(document);
    }

    ngOnDestroy(): void {
        this.eventManager.destroy( this.candidateEventSubscriber );
        this.eventManager.destroy( this.jobExperiencEventSubscriber );
        this.eventManager.destroy( this.educationEventSubscriber );
        this.eventManager.destroy( this.skillEventSubscriber );
        this.eventManager.destroy( this.documentEventSubscriber );
        this.eventManager.destroy( this.languageEventSubscriber );
        disableFooter(document);
    }

    reloadCandidate() {
        pipeToResponse( this.candidateService.find( this.candidate.id ) ).subscribe(
            ( res: ICandidate ) => this.candidate = res
        );
    }

    onUpdateAnagrafica() {
        const params = { candidate: Object.assign( {}, this.candidate ) };
        this.modalService.openLargeModal( CandidateUpdateAnagraficaDialogComponent, params )
            .then( res => {

            } ).catch( err => {

            } );
    }

    onUpdateJobExperience( experience: IJobExperience ) {
        const params = {
            currentJobExperience: experience ? Object.assign( {}, experience ) : new JobExperience(),
            candidateId: this.candidate.id
        };
        this.modalService.openLargeModal( CandidateUpdateJobExperiencesDialogComponent, params )
            .then( res => {

            } ).catch( err => {

            } );
    }

    onDeleteJobExperience( experience: IJobExperience ) {
        if ( !experience ) { return; }

        const params = { jobExperience: Object.assign( {}, experience ) };
        this.modalService.openLargeModal( CandidateDeleteJobExperienceDialogComponent, params )
            .then( res => {

            } ).catch( err => {

            } );
    }

    onUpdateSkill( skill: ISkill, skillType: any ) {
        const params = {
            currentSkill: skill ? Object.assign( {}, skill ) : new Skill(),
            candidateId: this.candidate.id,
            skillType
        };

        this.modalService.openLargeModal( CandidateUpdateSkillDialogComponent, params )
            .then( res => {

            } ).catch( err => {

            } );
    }

    onDeleteSkill( skill: ISkill ) {
        if ( !skill ) { return; }

        const params = {
            skill
        };

        this.modalService.openLargeModal( CandidateDeleteSkillDialogComponent, params )
            .then( res => {

            } ).catch( err => {

            } );
    }

    onUpdateEducation( education: IEducation, educationType: any ) {
        const params = {
            currentEducation: education ? Object.assign( {}, education ) : new Education(),
            candidateId: this.candidate.id,
            educationType
        };

        this.modalService.openLargeModal( CandidateUpdateEducationDialogComponent, params )
            .then( res => {

            } ).catch( err => {

            } );
    }

    onDeleteEducation( education: IEducation ) {
        if ( !education ) { return; }

        const params = {
            education
        };

        this.modalService.openLargeModal( CandidateDeleteEducationDialogComponent, params )
            .then( res => {

            } ).catch( err => {

            } );
    }

    onUpdateCV( document: IDocument, documentType: any ) {
        const params = {
            currentDocument: document ? Object.assign( {}, document ) : new Document(),
            candidateId: this.candidate.id,
            documentType
        };

        this.modalService.openLargeModal( CandidateUpdateDocumentDialogComponent, params )
            .then( res => {

            } ).catch( err => {

            } );
    }

    onDeleteCV( document: IEducation ) {
        if ( !document ) { return; }

        const params = {
            document
        };

        this.modalService.openLargeModal( CandidateDeleteDocumentDialogComponent, params )
            .then( res => {

            } ).catch( err => {

            } );
    }

    changeCurrentLanguage() {
        this.languageService.getCurrent()
            .then( lang => {
                this.currentLanguage = lang;
            } ).catch( err => {
                this.currentLanguage = LANGUAGES[0];
            } );
    }

    reloadJobExperiences() {
        pipeToResponse( this.jobExperienceService.findAll( this.candidate.id ) ).subscribe(
            ( jobs: IJobExperience[] ) => {
                this.jobExperiences = jobs;
                this.sortJobExperiences( this.jobExperiences );
                this.currentJobExperience = this.jobExperiences.find( je => je.current );
            }
        );
    }

    reloadEducations() {
        pipeToResponse( this.educationService.findAll( this.candidate.id ) ).subscribe(
            ( educations: IEducation[] ) => {
                this.educations = educations.filter( education => education.educationType === EducationType.EDUCATION );
                this.certifications = educations.filter( education => education.educationType === EducationType.CERTIFICATION );
                this.sortEducations( this.educations );
                this.sortEducations( this.certifications );
            }
        );
    }

    reloadSkills() {
        pipeToResponse( this.skillService.findAll( this.candidate.id ) ).subscribe(
            ( skills: ISkill[] ) => {
                this.otherSkills = skills.filter( skill => skill.skillTagType === SkillType.OTHER );
                this.languageSkills = skills.filter( skill => skill.skillTagType === SkillType.LANGUAGE );
                this.sortSkills( this.otherSkills );
                this.sortSkills( this.languageSkills );
            }
        );
    }

    sortSkills( skills: ISkill[] ) {
        skills.sort(( a: ISkill, b: ISkill ) => {
            if ( this.skillLevel.indexOf( a.level ) < this.skillLevel.indexOf( b.level ) ) {
                return 1;
            }
            if ( this.skillLevel.indexOf( a.level ) > this.skillLevel.indexOf( b.level ) ) {
                return -1;
            } else {
                return 0;
            }
        } );
    }

    sortEducations( educations: IEducation[] ) {
        educations.sort(( a: IEducation, b: IEducation ) => {
            if ( a.current ) {
                return b.current ? 0 : -1;
            } else if ( b.current ) {
                return 1;
            }
            if ( b.endDate && a.endDate ) {
                return moment.utc( b.endDate ).diff( moment.utc( a.endDate ) );
            } else {
                return 1;
            }
        } );
    }

    sortJobExperiences( jobExperiences: IJobExperience[] ) {
        jobExperiences.sort(( a: IJobExperience, b: IJobExperience ) => {
            if ( a.current ) {
                return b.current ? 0 : -1;
            } else if ( b.current ) {
                return 1;
            }
            if ( b.endDate && a.endDate ) {
                return moment.utc( b.endDate ).diff( moment.utc( a.endDate ) );
            } else {
                return 1;
            }
        } );
    }

    reloadDocuments() {
        pipeToResponse( this.documentService.findAll( this.candidate.id ) ).subscribe(
            ( allDocuments: IDocument[] ) => {
                this.documents = allDocuments.filter( doc => !doc.documentType || doc.documentType !== DocumentType.CV );
                this.documentsCv = allDocuments.filter( doc => doc.documentType === DocumentType.CV );
            }
        );
    }

    selectFile() {
        document.getElementById( 'inputPicture' ).click();
    }

    processFile( imageInput: any ) {
        const file: File = imageInput.files[0];
        if ( file.size > MAX_SIZE_PROFILE_IMAGE ) {
            alert( 'L\'immagine non può superare i 2MB' );
            return;
        }

        const reader = new FileReader();
        reader.addEventListener( 'load', ( event: any ) => {
            this.fileService.uploadUserProfileImg( file ).subscribe(
                ( res: HttpResponse<any> ) => {
                    this.candidate.imageUrl = res.body.fileName;
                    this.accountService.identity( true );
                },
                ( err: HttpErrorResponse ) => {
                    this.jhiAlertService.error( err.message, null, null );
                } );
        } );
        reader.readAsDataURL( file );
    }

    getJobExperienceTimePassed( job: IJobExperience ) {
        return this.helper.getExperienceTimePassed( job.startDate, job.endDate, job.current );
    }

    dowloadCv( idDocument: number ) {
        pipeToResponse( this.documentService.find( idDocument ) ).subscribe(
            ( res: IDocument ) => {
                return this.dataUtils.openFile( res.contentContentType, res.content );
            },
            ( err: HttpErrorResponse ) => this.jhiAlertService.error( err.message, null, null )
        );
    }

    getPercentualeEfficaciaProfilo(): number {
        let perc = 0;
        if ( this.otherSkills && this.otherSkills.length > 0 ) {
            if ( this.otherSkills.length >= 5 ) {
                perc += 25;
            } else {
                perc += ( this.otherSkills.length * 5 );
            }
        }
        if ( this.languageSkills && this.languageSkills.length > 0 ) {
            if ( this.languageSkills.length <= 2 ) {
                perc += ( this.languageSkills.length * 10 );
            } else {
                perc += perc + 20;
            }
        }
        if ( this.jobExperiences && this.jobExperiences.length > 0 ) {
            if ( this.jobExperiences.length <= 2 ) {
                perc += ( this.jobExperiences.length * 20 );
            } else {
                perc += 40;
            }
        }
        if ( this.educations && this.educations.length > 0 ) {
            perc += 5;
        }
        if ( this.documentsCv && this.documentsCv.length > 0 ) {
            perc += 10;
        }
        return perc;
    }

    previousState() {
        window.history.back();
    }
}
