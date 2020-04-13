import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {QueryRequest} from '../model/QueryRequest';
import {QueryResponse} from '../model/QueryResponse';
import {Observable} from 'rxjs';

const httpOptions = {
  headers: new HttpHeaders( {
    'Content-Type': 'application/json'
  })
};

@Injectable({
  providedIn: 'root'
})
export class QueryService {

  backendUrl = 'http://localhost:8080';
  searchUrl = '/';

  constructor(private httpClient: HttpClient) {}

  searchPost(queryRequest: QueryRequest): Observable<QueryResponse> {
    return this.httpClient.post<QueryResponse>(this.backendUrl + this.searchUrl, queryRequest, httpOptions);
  }
}
