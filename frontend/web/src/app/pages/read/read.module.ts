import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {ReadComponent} from "./read.component";
import {ReadRoutingModule} from "./read-routing.module";
import { PdfViewerModule } from 'ng2-pdf-viewer';
import {ButtonModule} from "primeng/button";




@NgModule({
  declarations: [
    ReadComponent,
  ],
  imports: [
    CommonModule,
    ReadRoutingModule,
    PdfViewerModule,
    ButtonModule,
  ]
})
export class ReadModule { }
