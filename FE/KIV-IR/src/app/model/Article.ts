export class Article {

  public id: number;
  public downloadedDate: string;
  public title: string;
  public author: string;
  public content: string;
  public url: string;


  constructor(id: number, downloadedDate: string, title: string, author: string, content: string, url: string) {
    this.id = id;
    this.downloadedDate = downloadedDate;
    this.title = title;
    this.author = author;
    this.content = content;
    this.url = url;
  }
}
