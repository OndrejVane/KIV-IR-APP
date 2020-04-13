export class SearchRequest {
  public query: string;
  public date: Date;

  constructor(query: string, date: Date) {
    this.query = query;
    this.date = date;
  }
}
