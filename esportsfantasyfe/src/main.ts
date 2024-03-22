import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';

import { AppModule } from './app/app.module';

/**
 * This is the entry point of the application. It is responsible for bootstrapping the application module.
 */
platformBrowserDynamic().bootstrapModule(AppModule)
  .catch(err => console.error(err));
