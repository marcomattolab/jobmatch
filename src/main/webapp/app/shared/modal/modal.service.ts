import {Injectable} from '@angular/core';
import {NgbModal, NgbModalOptions} from '@ng-bootstrap/ng-bootstrap';

interface ModalInjectionParameters {
    [key: string]: any;
}

@Injectable({
    providedIn: 'root'
})
export class ModalService {
    readonly HUGE_STATIC: NgbModalOptions = {size: 'lg', backdrop: 'static'};
    readonly LARGE_STATIC: NgbModalOptions = {size: 'lg', backdrop: 'static'};
    readonly SMALL_STATIC: NgbModalOptions = {size: 'sm', backdrop: 'static'};

    constructor(private ngbModalService: NgbModal) {
    }

    openHugeModal(component, params?: ModalInjectionParameters): Promise<any> {
        return this.openModal(component, this.HUGE_STATIC, params);
    }

    openLargeModal(component, params?: ModalInjectionParameters): Promise<any> {
        return this.openModal(component, this.LARGE_STATIC, params);
    }

    openSmallModal(component, params?: ModalInjectionParameters): Promise<any> {
        return this.openModal(component, this.SMALL_STATIC, params);
    }

    private openModal(component, ngbModalOptions: NgbModalOptions, params?: ModalInjectionParameters): Promise<any> {
        const ngbModalRef = this.ngbModalService.open(component, ngbModalOptions);
        console.log(ngbModalRef);
        if (params) {
            Object.assign(ngbModalRef.componentInstance, params);
        }

        return ngbModalRef.result;
    }
}
