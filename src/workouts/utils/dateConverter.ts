import dayjs from "dayjs";
import { Dayjs } from "dayjs";
import utc from "dayjs/plugin/utc";

dayjs.extend(utc);

export const stringToDayjs = (stringDate : string) => {
    const [day, month, year] = stringDate.split('-').map(value => parseInt(value, 10));

    const date : Dayjs = dayjs(`${year}-${month}-${day}`);

    return date;
}