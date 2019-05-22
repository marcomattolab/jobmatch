import { Injectable } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import * as moment from 'moment';
import { FileService } from '../file/file.service';

@Injectable({ providedIn: 'root' })
export class Helper {
    constructor(protected translateService: TranslateService, protected fileService: FileService) { }

    loadImage(url: string, defaultUrl?: string): string {
        if (url) {
            return this.fileService.getDownloadFileUrl(url);
        } else {
            return defaultUrl ? defaultUrl : 'content/images/male-avatar.jpg';
        }
    }

    getTimePassed(startDate: moment.Moment): string {
        let output = '';
        let duration: moment.Duration;
        let durationUnit: number;
        let durationUnitDescription = '';
        let durationUnitDescriptionExt = '';
        if (startDate) {
            duration = moment.duration(moment().startOf('days').diff(startDate));
        }
        if (duration) {
            const days = duration.asDays();
            const weeks = duration.asWeeks();
            const months = duration.asMonths();
            const years = duration.asYears();

            if (years >= 1) {
                durationUnit = duration.years();
                if (years < 2) {
                    durationUnitDescription = 'year';
                } else {
                    durationUnitDescription = 'years';
                }
                durationUnitDescriptionExt = 'ago';
            } else if (months >= 1 && months < 12) {
                durationUnit = duration.months();
                if (months < 2) {
                    durationUnitDescription = 'month';
                } else {
                    durationUnitDescription = 'months';
                }
                durationUnitDescriptionExt = 'ago';
            } else if (weeks >= 1 && weeks < 5) {
                durationUnit = duration.weeks();
                if (weeks < 2) {
                    durationUnitDescription = 'week';
                } else {
                    durationUnitDescription = 'weeks';
                }
                durationUnitDescriptionExt = 'ago';
            } else if (days < 1) {
                durationUnitDescription = 'today';
            } else if (days >= 1 && days < 2) {
                durationUnitDescription = 'yesterday';
            } else if (days < 7) {
                durationUnit = duration.days();
                durationUnitDescription = 'days';
                durationUnitDescriptionExt = 'ago';
            }
        }
        if (durationUnit && durationUnit > 0) {
            output = '' + durationUnit;
        }
        if (durationUnitDescription) {
            output += ' ' + this.translateService.instant('global.timePassed.' + durationUnitDescription);
            if (durationUnitDescriptionExt) {
                output += ' ' + this.translateService.instant('global.timePassed.ago');
            }
        }
        return output;
    }

    getExperienceTimePassed(startDate: moment.Moment, endDate: moment.Moment, current: boolean) {
        let duration: moment.Duration;
        let output = '';
        if (current) {
            duration = moment.duration(moment().diff(startDate));
        } else if (endDate) {
            duration = moment.duration(endDate.diff(startDate));
        }
        if (duration) {
            const years = duration.years();
            const months = duration.months();

            if (years) {
                output += years;
                if (years > 1) {
                    output += ' ' + this.translateService.instant('global.timePassed.years');
                } else {
                    output += ' ' + this.translateService.instant('global.timePassed.year');
                }

            }
            if (months) {
                if (years) {
                    output += ', ';
                }
                output += months;
                if (months > 1) {
                    output += ' ' + this.translateService.instant('global.timePassed.months');
                } else {
                    output += ' ' + this.translateService.instant('global.timePassed.month');
                }
            }
            if (!years && !months) {
                output += this.translateService.instant('global.timePassed.lessThanAMonth');
            }
        }
        return output;
    }
}

export const isValidId = (id: any): boolean => id && id > 0;

export function enableFooter(document: Document): void {
    (document.querySelector('#footerContainer') as HTMLElement).style.display = 'block';
}

export function disableFooter(document: Document): void {
    (document.querySelector('#footerContainer') as HTMLElement).style.display = 'none';
}
