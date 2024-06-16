import { v4 as uuid } from "uuid";
import {piece1,piece2,piece3,piece4,piece5} from '../../assets';

/**
 * Category Database can be added here.
 * You can add category of your wish with different attributes
 * */

export const categories = [
  {
    _id: uuid(),
    categoryName: "Teracotta Ornaments and Home Decor",
    description:
      "literature in the form of prose, especially novels, that describes imaginary events and people",
    image: piece1,
  },
  {
    _id: uuid(),
    categoryName: "Macrame Based Handcraft",
    description:
      "Non-fiction is writing that gives information or describes real events, rather than telling a story.",
    image: piece2
  },
  {
    _id: uuid(),
    categoryName: "Moonj Based Handcraft",
    description:
      "Meant to cause discomfort and fear for both the character and readers, horror writers often make use of supernatural and paranormal elements in morbid stories that are sometimes a little too realistic.",
    image: piece3
  },
  {
    _id: uuid(),
    categoryName: "Banana fiber based ornaments & Home Decor",
    description:
      "Meant to cause discomfort and fear for both the character and readers, horror writers often make use of supernatural and paranormal elements in morbid stories that are sometimes a little too realistic.",
    image: piece4
  },
  {
  _id: uuid(),
    categoryName: "Jute Bags and Allied Products",
    description:
      "Meant to cause discomfort and fear for both the character and readers, horror writers often make use of supernatural and paranormal elements in morbid stories that are sometimes a little too realistic.",
    image: piece5
  },
  // {
  // _id: uuid(),
  //   categoryName: "Miscellaneous Products",
  //   description:
  //     "Meant to cause discomfort and fear for both the character and readers, horror writers often make use of supernatural and paranormal elements in morbid stories that are sometimes a little too realistic.",
  //   image: ethnic1
  // },
];
