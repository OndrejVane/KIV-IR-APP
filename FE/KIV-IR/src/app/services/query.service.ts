import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {QueryRequest} from '../model/QueryRequest';
import {QueryResponse} from '../model/QueryResponse';
import {Observable} from 'rxjs';
import {Article} from '../model/Article';
import {Message} from '../model/Message';

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
  article = '/article';
  setModel = '/set';
  initializationMyData = '/initmydata';
  initializationEvalData = '/initevaldata';
  saveToFile = '/save';
  loadFromFile = '/load';
  delete = '/delete';

  constructor(private httpClient: HttpClient) {}

  searchPost(queryRequest: QueryRequest): Observable<QueryResponse> {
    return this.httpClient.post<QueryResponse>(this.backendUrl + this.searchUrl, queryRequest, httpOptions);
  }

  getAllArticles(): Observable<Article[]> {
    return this.httpClient.get<Article[]>(this.backendUrl + this.article, httpOptions);
  }

  addArticle(newArticle: Article): Observable<boolean> {
    return this.httpClient.post<boolean>(this.backendUrl + this.article, newArticle, httpOptions);
  }

  setIndexModel(isVectorModel: boolean): Observable<boolean> {
    return this.httpClient.post<boolean>(this.backendUrl + this.setModel, isVectorModel, httpOptions);
  }

  initArticles(): Observable<Message> {
    return this.httpClient.get<Message>(this.backendUrl + this.initializationMyData, httpOptions);
  }

  initEvalArticles(): Observable<Message> {
    return this.httpClient.get<Message>(this.backendUrl + this.initializationEvalData, httpOptions);
  }

  saveIndexToFile(): Observable<Message> {
    return this.httpClient.get<Message>(this.backendUrl + this.saveToFile, httpOptions);
  }

  loadIndexFromFile(): Observable<Message> {
    return this.httpClient.get<Message>(this.backendUrl + this.loadFromFile, httpOptions);
  }

  deleteAll(): Observable<Message> {
    return this.httpClient.get<Message>(this.backendUrl + this.delete, httpOptions);
  }
}
