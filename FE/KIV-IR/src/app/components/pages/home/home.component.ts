import {Component, Input, OnInit} from '@angular/core';
import {QueryService} from '../../../services/query.service';
import {QueryRequest} from '../../../model/QueryRequest';
import {QueryResponse} from '../../../model/QueryResponse';
import {ResultTableComponent} from './result-table/result-table.component';
import {Article} from '../../../model/Article';

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
    this.setVectorModel();
  }

  onSubmit() {
    this.response = null;
    this.queryService.searchPost(new QueryRequest(this.expression, new Date())).subscribe( response => {
      this.response = response;
    });
  }

  setVectorModel() {
    this.queryService.setIndexModel(true).subscribe( response => {
      console.log('Vector model');
    });
  }

  setBooleanModel() {
    this.queryService.setIndexModel(false).subscribe( response => {
      console.log('Boolean model');
    });
  }

}
