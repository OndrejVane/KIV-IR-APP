export class SearchRequest {
  public expression: string;
  public date: Date;

  constructor(expression: string, date: Date) {
    this.expression = expression;
    this.date = date;
  }
}
