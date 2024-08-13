import { Pipe, PipeTransform } from '@angular/core';
import { CurrencyPipe } from '@angular/common';

@Pipe({
  name: 'esfcurrency',
})
export class EsfcurrencyPipe implements PipeTransform {
  constructor(private currencyPipe: CurrencyPipe) {}

  transform(
    value: number | null | undefined,
    currencyCode: string = 'USD',
    display: 'code' | 'symbol' | 'symbol-narrow' | string | boolean = 'symbol',
    digitsInfo: string = '1.2-2',
    locale: string = 'en-US'
  ): string {
    if (value == null) {
      return '';
    }

    let formattedValue = this.currencyPipe.transform(
      value,
      currencyCode,
      'symbol',
      digitsInfo,
      locale
    );

    if (formattedValue) {
      formattedValue = formattedValue.replace(/[^0-9.,]/g, '').trim();
      return `${formattedValue} 🪙`;
    }

    return '';
  }
}
