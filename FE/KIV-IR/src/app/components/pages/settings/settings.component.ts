import {Component, OnInit} from '@angular/core';
import {Message} from '../../../model/Message';
import {QueryService} from '../../../services/query.service';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css']
})
export class SettingsComponent implements OnInit {

  message: Message = null;
  sentRequest = false;
  successfulResponse = false;

  constructor(private queryService: QueryService) {
  }

  ngOnInit() {
  }

  setProgressBar(value: boolean) {
    if (value) {
      this.sentRequest = value;
      this.successfulResponse = false;
    } else {
      this.sentRequest = value;
      this.successfulResponse = true;
    }
  }

  init() {
    this.setProgressBar(true);
    this.queryService.initArticles().subscribe(response => {
      this.message = response;
      this.setProgressBar(false);
    });
  }

  save() {
    this.setProgressBar(true);
    this.queryService.saveIndexToFile().subscribe(response => {
      this.message = response;
      this.setProgressBar(false);
    });
  }

  load() {
    this.setProgressBar(true);
    this.queryService.loadIndexFromFile().subscribe(response => {
      this.message = response;
      this.setProgressBar(false);
    });
  }

  delete() {
    this.setProgressBar(true);
    this.queryService.deleteAll().subscribe(response => {
      this.message = response;
      this.setProgressBar(false);
    });
  }
}
