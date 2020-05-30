export class Article {

  public id: number;
  public downloadedDate: Date;
  public title: string;
  public author: string;
  public content: string;
  public url: string;
  public score: number;
  public rank: number;


  constructor(id: number, downloadedDate: Date, title: string, author: string, content: string, url: string) {
    this.id = id;
    this.downloadedDate = downloadedDate;
    this.title = title;
    this.author = author;
    this.content = content;
    this.url = url;
  }
}
