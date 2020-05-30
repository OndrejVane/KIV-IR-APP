import { Component, OnInit } from '@angular/core';
import {Message} from '../../../model/Message';
import {QueryService} from '../../../services/query.service';
import {QueryRequest} from '../../../model/QueryRequest';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css']
})
export class SettingsComponent implements OnInit {

  message: Message = null;

  constructor( private queryService: QueryService ) { }

  ngOnInit() {
  }


  init() {
    this.queryService.initArticles().subscribe( response => {
      this.message = response;
    });
  }

  save() {
    this.queryService.saveIndexToFile().subscribe( response => {
      this.message = response;
    });
  }

  load() {
    this.queryService.loadIndexFromFile().subscribe( response => {
      this.message = response;
    });
  }

  delete() {
    this.queryService.deleteAll().subscribe( response => {
      this.message = response;
    });
  }
}
