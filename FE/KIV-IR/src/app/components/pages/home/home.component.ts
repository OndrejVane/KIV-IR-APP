import { Component, OnInit } from '@angular/core';
import {QueryService} from '../../../services/query.service';
import {QueryRequest} from '../../../model/QueryRequest';
import {QueryResponse} from '../../../model/QueryResponse';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  expression: string;
  response: QueryResponse = null;

  constructor( private queryService: QueryService) { }

  ngOnInit() {
  }

  onSubmit() {
    this.queryService.searchPost(new QueryRequest(this.expression, new Date())).subscribe( response => {
      this.response = response;
    });
  }

}
