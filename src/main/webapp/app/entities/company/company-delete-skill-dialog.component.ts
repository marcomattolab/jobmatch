import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ICompanySkill } from 'app/shared/model/company-skill.model';
import { JhiEventManager } from 'ng-jhipster';
import { CompanySkillService } from '../company-skill/company-skill.service';

@Component({
    selector: 'jhi-skill-delete-dialog',
    templateUrl: './company-delete-skill-dialog.component.html'
})
export class CompanyDeleteSkillDialogComponent {
    skill: ICompanySkill;

    constructor(
        protected companySkillService: CompanySkillService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) { }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.companySkillService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'companySkillsModified',
                content: 'Deleted an jobExperience'
            });
            this.activeModal.dismiss(true);
        });
    }
}

/*
@Component({
    selector: 'jhi-skill-delete-popup',
    template: ''
})
export class CompanyDeleteSkillPopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) { }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ skill }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CompanyDeleteSkillDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.skill = skill;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/candidate', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/candidate', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}*/
