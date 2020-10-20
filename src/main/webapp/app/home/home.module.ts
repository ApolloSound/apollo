import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ApolloSharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';
import { TracksComponent } from './sections/tracks/tracks.component';
import { PlaylistsComponent } from './sections/playlists/playlists.component';
import { TrackComponent } from './sections/tracks/track/track.component';

@NgModule({
  imports: [ApolloSharedModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [TracksComponent, HomeComponent, PlaylistsComponent, TrackComponent]
})
export class ApolloHomeModule {}
