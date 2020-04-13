import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {SearchRequest} from '../model/QueryRequest';
import {SearchResponse} from '../model/QueryResponse';
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

  searchPost(searchRequest: SearchRequest): Observable<SearchResponse> {
    return this.httpClient.post<SearchResponse>(this.backendUrl + this.searchUrl, searchRequest, httpOptions);
  }
}
