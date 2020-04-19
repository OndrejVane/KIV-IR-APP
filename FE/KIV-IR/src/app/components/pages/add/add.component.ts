import {Component, OnInit} from '@angular/core';
import {Article} from '../../../model/Article';
import {FormGroup} from '@angular/forms';
import {QueryService} from '../../../services/query.service';

@Component({
  selector: 'app-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.css']
})
export class AddComponent implements OnInit {

  isSaved = false;

  constructor( private service: QueryService) { }

  ngOnInit() {
  }

  submitArticle(form: FormGroup) {
    console.log(form.value);
    console.log(form.value.url);

    const article: Article = new Article(0, new Date(), form.value.title, form.value.author, form.value.content, form.value.url);

    this.service.addArticle(article).subscribe(response => {
      this.isSaved = response;
    });
    form.reset();
  }
}
