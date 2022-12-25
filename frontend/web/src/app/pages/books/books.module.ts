import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {BooksComponent} from "./books.component";
import {DataViewModule} from "primeng/dataview";
import {AppRoutingModule} from "../../app-routing.module";
import {BooksRoutingModule} from "./books-routing.module";

@NgModule({
  declarations: [
    BooksComponent,
  ],
  imports: [
    CommonModule,
    DataViewModule,
    BooksRoutingModule
  ]
})
export class BooksModule { }
