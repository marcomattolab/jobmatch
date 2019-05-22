import { NgbDateParserFormatter, NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';
import * as moment from 'moment';

export class MomentDateFormatter extends NgbDateParserFormatter {

    readonly DT_FORMAT = 'DD/MM/YYYY';

    parse(value: string): NgbDateStruct {
        if (value && moment(value, this.DT_FORMAT, true).isValid()) {
            const date =  moment(value, this.DT_FORMAT, true).toDate();
            return {
                year: date.getFullYear(),
                month: date.getMonth() + 1,
                day: date.getDate()
            };
        }
        return null;
    }

    format(date: NgbDateStruct): string {
        if (!date) { return ''; }
        const mdt = moment([date.year, date.month - 1, date.day]);
        if (!mdt.isValid()) { return ''; }
        return mdt.format(this.DT_FORMAT);
    }
}
