import {Component} from '@angular/core';
import {MatGridListModule} from '@angular/material/grid-list';

/**
 * @title Basic grid-list
 */
@Component({
  selector: 'gallery-page',
  styleUrl: 'gallery.scss',
  templateUrl: 'gallery.html',
  imports: [MatGridListModule],
})
export class GalleryPage {}