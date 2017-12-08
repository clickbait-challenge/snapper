# Detection of Clickbait posts #

The snapper clickbait detector.
A framework for detecting clickbait posts using a two-level classification approach. A set of text-based features are extracted and combined for training 65 classifiers. The outputs of these classifiers are used as a second-level feature vector.

## Getting Started ##

## Requirements ##

1. JDK 1.7 or greater
2. Python modules
	- numpy
	- scipy
	- SocketServer
	- Scikit Learn
	- networkx
3. mongoDB

## Usage
------------------
The framework performs feature extraction on a new post and then classify it as clickbait or not. 

**Define variables in src/main/resources/local.properties:** <br />

	- resources: path where the resources are stored.
	- stopClickbaitPython: path to python script executing the external feature extraction. (/resources/stop_clickbait_paper/mass_extractor.py)
	
A set of features is extracted using an external framework. Refer to https://github.com/bhargaviparanjape/clickbait for installation instructions. Replace the utility.py with /resources/stop_clickbait_paper/utility.py

**Initialize parameters** <br />

	- auth = true or false. Whether to use authentication for connecting to mongoDB. Default value = false;
		if auth = true define credentials in mongo.properties file (/src/main/resources)
	- mainDB =  name of the main database where intermediate data will be stored.
	- ProbCollection1stLevel = collection name where the probabilities that form the feature vector for the second level classification are stored.
	

Main class *ClickbaitChallenge* in *gr.iti.mklab* package. Provide command line arguments:

	Input arguments:

		-i input_path: path of a text file (instances.jsonl) containing the post objects. Post file format (1 line per post):
			{
				"id": "608999590243741697",
				"postText": ["Some people are such food snobs"],
				"postMedia": ["608999590243741697.png"],
				"targetTitle": "Some people are such food snobs",
			}     

	 	-o output_path: path where a file results.jsonl will be stored containing clickbait scores in the range [0,1], where a value of 1.0 denotes that a post is heavily click baiting. Output file format:
			{"id": "608999590243741697", "clickbaitScore": 1.0}

## Citations

	@article{papadopoulou2017two,
		title={A Two-Level Classification Approach for Detecting Clickbait Posts using Text-Based Features},
		author={Papadopoulou, Olga and Zampoglou, Markos and Papadopoulos, Symeon and Kompatsiaris, Ioannis},
		journal={arXiv preprint arXiv:1710.08528},
		year={2017}
	}
		
## Contact for further details
------------------
Olga Papadopoulou (<olgapapa@iti.gr>) <br />
Symeon Papadopoulos (<papadop@iti.gr>)
