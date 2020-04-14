import {Article} from './Article';

export class QueryResponse {
  public expression: string;
  public date: Date;
  public articles: Article[];

  constructor(expression: string, date: Date, articles: Article[]) {
    this.expression = expression;
    this.date = date;
    this.articles = articles;
  }
}
