package model;

public enum ParcelStatus {  
	REQUEST_INITIATED,
    ASSIGNED_TO_DRIVER,
    PICKED_FROM_SENDER,     //rn in its source city, can tell source city center address(destination) and source home(source)
    DROPPED_AT_LOCALCENTER, //now i know that address too
    ASSIGNED_TRANSIT, //newone
    IN_TRANSIT,
    AT_DESTINATION_CENTER,             //from source city to destination city center, have both address
    ASSIGNED_DELIVERY,
    OUT_FOR_DELIVERY,       //from destination city center to destination home (have both addresses)
    DELIVERED,          //end, wont be updated now
    FINISHED
}


//for intercity

/*
 * driver having parcels in array with status at_Desitnation_center will be showed to pickup
 * driver having parcels in array with status out_for delivery will be showed in interface to dropoff
 * driver will wait till user recieves the parcel
 * driver having parcels in array with status request_initiated will be showd to pickup_from_sender
 * driver having parcels in array with status assigned_to_driver will be showd to dropoff_At_center
 * */


//for intracity

/*
 * driver having parcels in array with dropped_at_localcenter will be showed to pickup
 * driver having parcels in array with intrnasit will be showed to drop off at destination center
 * */


//for center manager

//if same destination city, assign it to some driver and change status as will do in driver case
//if for different assign to some intercity driver and change status












/////////////////////////////////////////////////////////////////////////////////////////
/*
  
               the parcel route will be same forever since creation
               the drivers assigned this will have routes changed at each status of 
               the parcel
               and the comments above explain how we do that

*/
////////////////////////////////////////////////////////////////////////////////////////


//make a variable ParcelStatus status and status.name gives it in string format 