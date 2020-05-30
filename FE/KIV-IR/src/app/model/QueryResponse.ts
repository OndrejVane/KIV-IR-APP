import {Article} from './Article';

export class QueryResponse {
  public expression: string;
  public numberOfAllResults: number;
  public articles: Article[];

  constructor(expression: string, numberOfAllResults: number, articles: Article[]) {
    this.expression = expression;
    this.numberOfAllResults = numberOfAllResults;
    this.articles = articles;
  }
}
