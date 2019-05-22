import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICompanySkill } from 'app/shared/model/company-skill.model';

@Component({
    selector: 'jhi-company-skill-detail',
    templateUrl: './company-skill-detail.component.html'
})
export class CompanySkillDetailComponent implements OnInit {
    companySkill: ICompanySkill;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ companySkill }) => {
            this.companySkill = companySkill;
        });
    }

    previousState() {
        window.history.back();
    }
}
