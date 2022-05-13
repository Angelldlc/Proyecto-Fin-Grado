using System;
using System.Collections.Generic;
using System.Text;

namespace CRUDOurTravel_Entities
{
    public class clsDestination
    {
        public string TripPlanningId { get; set; }
        public string CityName { get; set; }
        public double AcommodationCosts { get; set; }
        public double TransportationCosts { get; set; }
        public double FoodCosts { get; set; }
        public double TourismCosts { get; set; }
        public string Description  { get; set; }
        public DateTime StartDate { get; set; }
        public DateTime EndDate { get; set; }
        public string TravelStay { get; set; }
        public string TouristAttractions { get; set; }
    }
}
