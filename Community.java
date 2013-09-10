package kinglem.Multitudes

import kinglem.Multitudes.Family;

	/**
	*	What does this class need to do? 
	*		-Keep track of the Community's ID
	*		-Keep track of Community's center coordinate (villagers control how far away they settle)
	*		-Allow families to be added to/removed from the Community
	*		-Manages changes to how much this Community likes other Family-extended objects
	*/

class Community extends Family{
	// id structure: {indv}{faml}{comm}{trib}{natn}{frendval}{????}
	private HashMap<int,Family> relationships = new HashMap<int,Family>();

	//Gets called when two or more families create a new community
	public Community(Family family1, Family family2){
		//Randomly generate new community ID
		Random rand = new Random();
		
		id = (short) rand.nextInt(32);
		id = id << 20;

		//Assign new Community ID
		family1.setId();
		family2.setId();

		//Assign families to relationships.
		relationships.add(family1.getId(),family1);
		relationships.add(family2.getId(),family2);

	}

	//setId only called when adding community to a tribe
	//Assumes tribe's ID is not going to be changing during the lifetime of the world.
	private void setId(int tribeId, boolean changeTribe){
		//Since tribeId's 12 most significant ID bits will be zero, if the four least significant
		//ID bits of 'this' are zero, then XOR the two ids together and store the result in id.
		//This assigns the tribe's ID to this community only when no tribe's has yet been set.
		int tempId = id >> 20;

		if( ( !(id & (1<<0)) || !(id & (1<<1)) || !(id & (1<<2)) || !(id & (1<<3)) ) && changeTribe){
			id = id^tribeId;

			for(int rel : relationships){
				rel.setId(this.id);
			}
		//Already has a tribe, but being assigned a new tribe.
		}else if(changeTribe){
			//Bit shift to remove old tribe designation.
			id = id >> 4;
			id = id << 4;

			//XOR in new tribe designation
			id = id^tribeId;
			
			for(int rel : relationships){
				rel.setId(this.id);
			}
		}
		//Already has a tribe, not being assigned a new tribe.
		else{
			return;
		}
	}

	//Returns all the families tracked by this Community object
	public HashMap<int,Family> getRelationships(){
		return relationships;
	}

	public void addFamily(Family newFam){
		if(!relationships.contains(newFam.getId())){
			newFam.setId(this.id);
			relationships.add(newFam.getId(), newFam);
		}
	}

	//I'm going to assume that exFam will extricate the community & tribe IDs from its
	//ID when it 'leaves' the 'community'.
	public void removeFamily(Family exFam){
		if(relationships.contains(exFam.getId)){
			relationships.remove(exFam.getId());
		}
	}

	//Increases or decreases the friendship value this Community has with the passed-in Family.
	//Amount may be greater than or less than 0, but the total of fVal and amount must be between 0 and 256
	public void affectFriendship(Family family1, short amount){
		//Isolate the "friendship value"
		int fVal = id << 20;
		fVal = fVal >> 24;

		//Is the total friendship value out of the accepted range?
		//If so, set fVal to the appropriate extreme
		if(amount + fVal > 256){
			fVal = 256;
		}else if(amount + fVal < 0){
			fVal = 0;
		}else{ //Otherwise, add fVal to amount and assign to fVal
			fVal += amount;
		}

		id = (((id >> 12) << 8) + fVal) << 4;
	}


}