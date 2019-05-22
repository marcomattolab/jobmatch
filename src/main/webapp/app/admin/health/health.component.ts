import { Component, OnInit, OnDestroy } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { JhiHealthService } from './health.service';
import { JhiHealthModalComponent } from './health-modal.component';
import { enableFooter, disableFooter } from 'app/shared';

@Component({
    selector: 'jhi-health',
    templateUrl: './health.component.html'
})
export class JhiHealthCheckComponent implements OnInit, OnDestroy {
    healthData: any;
    updatingHealth: boolean;

    constructor(private modalService: NgbModal, private healthService: JhiHealthService) {}

    ngOnInit() {
        this.refresh();
        enableFooter(document);
    }

    ngOnDestroy() {
        disableFooter(document);
    }

    baseName(name: string) {
        return this.healthService.getBaseName(name);
    }

    getBadgeClass(statusState) {
        if (statusState === 'UP') {
            return 'badge-success';
        } else {
            return 'badge-danger';
        }
    }

    refresh() {
        this.updatingHealth = true;

        this.healthService.checkHealth().subscribe(
            health => {
                this.healthData = this.healthService.transformHealthData(health);
                this.updatingHealth = false;
            },
            error => {
                if (error.status === 503) {
                    this.healthData = this.healthService.transformHealthData(error.error);
                    this.updatingHealth = false;
                }
            }
        );
    }

    showHealth(health: any) {
        const modalRef = this.modalService.open(JhiHealthModalComponent);
        modalRef.componentInstance.currentHealth = health;
        modalRef.result.then(
            result => {
                // Left blank intentionally, nothing to do here
            },
            reason => {
                // Left blank intentionally, nothing to do here
            }
        );
    }

    subSystemName(name: string) {
        return this.healthService.getSubSystemName(name);
    }
}
