package kinglem.Multitudes;

import minecraft.Village

	/**
	*	@todo: Allow individuals to be removed from family
	*	@todo: Track relations with other villagers
	*	@todo: Allow for tracking of relationships with Family objects?
	*	@todo: setId functionality for communities, tribes, and nations
	*	@todo: Allow for moving the center of the villager
	*	@todo: Tracking of parent Community (for each, where last 8 bits are 0 and this.{comm} = {comm})
	*/

	/**
	*	The Family object will keep track of:
	*		-Individuals within the Family object
	*		-Its own id
	*		-The coordiantes of the family's 'center'
	*		-Family's relationships with other Family objects
	*/

class Family extends Village{
	// id structure: {indv}{faml}{comm}{trib}{natn}{frendval}{????}
	private int id;
	private HashMap<int,MultVillager> relationships = new HashMap<int,MultVillager>();
	private int locX, locY, locZ;

	private const short MAX_FRIEND = 256;
	private const short MIN_FRIEND = 0;

	public Family(MultVillager ind1, MultVillager ind2){
		//Randomly generate new community ID
		Random rand = new Random();
		
		id = (short) rand.nextInt(32);
		id = id << 24;

		//Assign new Community ID
		ind1.setId();
		ind2.setId();

		//Assign families to relationships.
		relationships.add(ind1.getId(),ind1);
		relationships.add(ind2.getId(),ind2);
	}

	public int getId(){
		return id;
	}

	//Returns the current x, y, and z of this Community object
	public Object getCoordinates(){
		return; //village center object?
	}

	//Sets coordinates for center of this Community. Allows for community relocation.
	public void setCoordinates(int x, int y, int z){
		this.locX = x;
		this.locY = y;
		this.locZ = z;
	}

	public void addIndividual(MultVillager ind1){
		if(!relationships.contains(ind1.getId())){
			relationships.add(ind1.getId(),ind1);
		}
	}

};

