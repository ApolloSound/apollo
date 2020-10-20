import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import './vendor';
import { ApolloSharedModule } from 'app/shared/shared.module';
import { ApolloCoreModule } from 'app/core/core.module';
import { ApolloAppRoutingModule } from './app-routing.module';
import { ApolloHomeModule } from './home/home.module';
import { ApolloEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { SfMainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';
import { ApolloPlayerModule } from 'app/layouts/player/player.module';
import { NotificationComponent } from './layouts/navbar/notification/notification.component';
import { ProfileComponent } from './layouts/navbar/profile/profile.component';
import { ApolloWidgetsModule } from 'app/layouts/widgets/widgets.module';

@NgModule({
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    ApolloSharedModule,
    ApolloCoreModule,
    ApolloHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    ApolloEntityModule,
    ApolloPlayerModule,
    ApolloWidgetsModule,
    ApolloAppRoutingModule
  ],
  declarations: [
    SfMainComponent,
    NotificationComponent,
    ProfileComponent,
    NavbarComponent,
    ErrorComponent,
    PageRibbonComponent,
    ActiveMenuDirective,
    FooterComponent
  ],
  bootstrap: [SfMainComponent]
})
export class ApolloAppModule {}
