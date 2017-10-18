import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Pessoa } from './pessoa.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PessoaService {

    private resourceUrl = 'api/pessoas';

    constructor(private http: Http) { }

    create(pessoa: Pessoa): Observable<Pessoa> {
        const copy = this.convert(pessoa);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(pessoa: Pessoa): Observable<Pessoa> {
        const copy = this.convert(pessoa);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Pessoa> {
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

    private convert(pessoa: Pessoa): Pessoa {
        const copy: Pessoa = Object.assign({}, pessoa);
        return copy;
    }
}
