import { Component, OnInit } from '@angular/core';
import { WizardService } from '../../services/wizard.service';
import { GqlPuppeteerWaitUntil } from '../../../generated/graphql';

export const isUrl = (value: string): boolean => {
  if (value.startsWith('http://') || value.startsWith('https://')) {
    try {
      new URL(value);
      return true;
    } catch (e) {
      return false;
    }
  } else {
    return isUrl(`https://${value}`);
  }
};

@Component({
  selector: 'app-getting-started',
  templateUrl: './getting-started.page.html',
  styleUrls: ['./getting-started.page.scss'],
})
export class GettingStartedPage implements OnInit {
  constructor(private readonly wizardService: WizardService) {}

  ngOnInit() {}

  async openWizard(url: string) {
    if (isUrl(url)) {
      await this.wizardService.openFeedWizard({
        fetchOptions: {
          websiteUrl: url,
          prerender: false,
          prerenderWaitUntil: GqlPuppeteerWaitUntil.Load,
          prerenderWithoutMedia: false,
          prerenderScript: ''
        },
      });
    }
  }
}
