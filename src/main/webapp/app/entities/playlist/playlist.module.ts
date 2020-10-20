import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ApolloSharedModule } from 'app/shared/shared.module';
import { PlaylistComponent } from './playlist.component';
import { PlaylistDetailComponent } from './playlist-detail.component';
import { PlaylistUpdateComponent } from './playlist-update.component';
import { PlaylistDeleteDialogComponent } from './playlist-delete-dialog.component';
import { playlistRoute } from './playlist.route';

@NgModule({
  imports: [ApolloSharedModule, RouterModule.forChild(playlistRoute)],
  declarations: [PlaylistComponent, PlaylistDetailComponent, PlaylistUpdateComponent, PlaylistDeleteDialogComponent],
  entryComponents: [PlaylistDeleteDialogComponent]
})
export class ApolloPlaylistModule {}
