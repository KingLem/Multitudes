package kinglem.Multitudes;

import minecraft.Village

	/**
	*	@todo: 
	*/

class Family extends Village{
	// id structure: {indv}{faml}{comm}{trib}{natn}{frendval}{????}
	private int id;
	private HashMap<short,Family> relationships = new HashMap<short,Family>();
	private int locX, locY, locZ;

	private const short MAX_FRIEND = 256;
	private const short MIN_FRIEND = 0;

	/**
	*	The Family object will keep track of:
	*		-Individuals within the Family object
	*		-Its own id
	*		-The coordiantes of the family's 'center'
	*/

	public Family(){

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

};

