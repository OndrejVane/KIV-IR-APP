import { Component, OnInit } from '@angular/core';
import {QueryService} from '../../../services/query.service';
import {Article} from '../../../model/Article';

@Component({
  selector: 'app-articles',
  templateUrl: './articles.component.html',
  styleUrls: ['./articles.component.css']
})
export class ArticlesComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }
}
