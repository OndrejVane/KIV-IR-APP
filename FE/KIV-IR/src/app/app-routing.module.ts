import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './components/pages/home/home.component';
import { AboutComponent } from './components/pages/about/about.component';
import { ArticlesComponent } from './components/pages/articles/articles.component';
import { AddComponent } from './components/pages/add/add.component';
import { SettingsComponent } from './components/pages/settings/settings.component';


const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'about', component: AboutComponent },
  { path: 'add', component: AddComponent },
  { path: 'articles', component: ArticlesComponent },
  { path: 'settings', component: SettingsComponent },
  ];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
