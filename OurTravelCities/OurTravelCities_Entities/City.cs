using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace OurTravelCities_Entities
{
    public class City
    {
        public City(string name, string photo)
        {
            this.name = name;
            this.photo = photo;
        }

        public City()
        {
            name = "";
            photo = "";
        }

        public string name { get; set; }
        public string photo { get; set; }


    }
}
