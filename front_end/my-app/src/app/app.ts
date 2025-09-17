// src/app/app.component.ts

import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';



@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    CommonModule,
  ],
  templateUrl: './app.html',
  styleUrls: ['./app.scss']
})
export class AppComponent {
  
}
