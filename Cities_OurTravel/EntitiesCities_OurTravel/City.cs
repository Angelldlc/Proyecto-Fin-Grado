using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace EntitiesCities_OurTravel
{
    public class City
    {
        public string name { get; set; }
        public string photo { get; set; }

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

    }
}
