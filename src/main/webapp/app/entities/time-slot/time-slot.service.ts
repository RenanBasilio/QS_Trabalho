import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { TimeSlot } from './time-slot.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class TimeSlotService {

    private resourceUrl = 'api/time-slots';

    constructor(private http: Http) { }

    create(timeSlot: TimeSlot): Observable<TimeSlot> {
        const copy = this.convert(timeSlot);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(timeSlot: TimeSlot): Observable<TimeSlot> {
        const copy = this.convert(timeSlot);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<TimeSlot> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(timeSlot: TimeSlot): TimeSlot {
        const copy: TimeSlot = Object.assign({}, timeSlot);
        return copy;
    }
}
