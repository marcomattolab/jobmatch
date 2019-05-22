import {Component, OnDestroy, OnInit} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {PlatformLocation} from '@angular/common';

@Component({
    selector: 'jhi-confirm-dialog',
    templateUrl: './confirm-dialog.component.html'
})
export class ConfirmDialogComponent implements OnInit, OnDestroy {
    title: string;
    message: string;
    translationParams: any;

    constructor(
        private activeModal: NgbActiveModal,
        private location: PlatformLocation) {

        location.onPopState(() => this.activeModal.dismiss());
    }

    ngOnInit(): void {
    }

    ngOnDestroy(): void {
    }

    clear() {
        this.activeModal.dismiss();
    }

    confirm() {
        this.activeModal.close(true);
    }
}
