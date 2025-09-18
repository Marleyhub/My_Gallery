import {Component} from '@angular/core';
import {MatGridListModule} from '@angular/material/grid-list';
import { ToolBar } from '../../tool-bar/tool-bar';

/**
 * @title Basic grid-list
 */
@Component({
  selector: 'gallery-page',
  styleUrl: 'gallery.scss',
  templateUrl: 'gallery.html',
  imports: [MatGridListModule, ToolBar],
})
export class GalleryPage {}