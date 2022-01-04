import { Component, Input, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { FeedService } from '../../services/feed.service';
import { GqlFeed } from '../../../generated/graphql';
import * as timeago from 'timeago.js';
import { firstValueFrom } from 'rxjs';

@Component({
  selector: 'app-feed-details',
  templateUrl: './feed-details.component.html',
  styleUrls: ['./feed-details.component.scss'],
})
export class FeedDetailsComponent implements OnInit {
  @Input()
  feed: GqlFeed;
  permissions = 'public';
  harvestInterval = 'default';

  constructor(
    private readonly modalController: ModalController,
    private readonly feedService: FeedService
  ) {}

  ngOnInit() {
    firstValueFrom(this.feedService.findById(this.feed.id)).then(
      ({ data, errors }) => {
        if (errors) {
        } else {
          this.feed = data.feed as GqlFeed;
        }
      }
    );
  }

  dismissModal() {
    return this.modalController.dismiss();
  }

  isOwner() {
    return this.modalController.dismiss();
  }

  getLastUpdatedAt(date: Date) {
    if (date) {
      return timeago.format(date);
    }
  }
}
