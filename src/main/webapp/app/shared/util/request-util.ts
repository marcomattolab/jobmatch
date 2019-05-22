import { HttpParams, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

export const createRequestOption = (req?: any): HttpParams => {
    let options: HttpParams = new HttpParams();
    if (req) {
        Object.keys(req).forEach(key => {
            if (key !== 'sort') {
                options = options.set(key, req[key]);
            }
        });
        if (req.sort) {
            req.sort.forEach(val => {
                options = options.append('sort', val);
            });
        }
    }
    return options;
};

// Utilizzo: pipeToResponse(service.find(id)) -> Ritorna l'oggetto della risposta senza l'HttpResponse
export function pipeToResponse<T>(obs: Observable<HttpResponse<T>>) {
    return obs.pipe(
        map((http: HttpResponse<T>) => http.body)
    );
}

// Utilizzo: await observableToPromiseNoThrow(service.query()) -> Ritorna la risposta senza throware eccezioni
export function observableArrayToPromiseNoThrow<T>(obs: Observable<T>) {
    return new Promise(async resolve => {
        try {
            resolve(await observableToPromiseNoThrow(obs));
        } catch (ex) {
            resolve([]);
        }
    });
}

// Utilizzo: await observableToPromiseNoThrow(service.find(id)) -> Ritorna la risposta senza throware eccezioni
export function observableVariableToPromiseNoThrow<T>(obs: Observable<T>) {
    return new Promise<T>(async resolve => {
        try {
            resolve(await observableToPromiseNoThrow(obs));
        } catch (ex) {
            resolve(null);
        }
    });
}

function observableToPromiseNoThrow<T>(obs: Observable<T>) {
    return new Promise<T>(async (resolve, reject) => {
        try {
            resolve(await obs.toPromise());
        } catch (ex) {
            reject();
        }
    });
}
