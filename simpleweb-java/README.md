# simpleweb-java

This is an ultra-simple web server written in java. It returns a welcome message and also print outs the server's IP address and listening TCP port. This is useful for demo purposes and load-balancing testing. It also looks for the environment variable $PORT0 and starts the server in that port if the variable exists. This is useful for deployment with Marathon and DC/OS.

The docker version runs on Alpine linux and is fairly lightweight.

## Installation

Pull the docker image into docker or marathon. Or download and run the .jar file locally.

## Usage

Docker image is available:

``` fernandosanchez/simpleweb-java ```

Or run locally as:

``` sudo java -jar simpleweb.jar ```

## Contributing

1. Fork it!
2. Create your feature branch: `git checkout -b my-new-feature`
3. Commit your changes: `git commit -am 'Add some feature'`
4. Push to the branch: `git push origin my-new-feature`
5. Submit a pull request :D

## Credits

Most of the code is coming from: 
http://www.java2s.com/Code/Java/Network-Protocol/ASimpleWebServer.htm

The code for detecting the server's IP addresses was found here:
http://stackoverflow.com/questions/8083479/java-getting-my-ip-address

## License

TODO: Write license
