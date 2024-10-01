<h1 align="center">🛒 Payment System for Grocery Store</h1>

<p align="center">
This project is a <strong>Java Spring Boot</strong> application for a payment system at a nearby grocery store. It allows items to be added to a cart, applies promotions, and calculates the total price and savings.
</p>

<h2>✨ Features</h2>
<ul>
  <li><strong>Add Items to Cart</strong>: Items can be added to the cart in any order.</li>
  <li><strong>Apply Promotions</strong>: The system supports various types of promotions, including:
    <ul>
      <li>Buy X Get Y Free</li>
      <li>Quantity-Based Price Override</li>
      <li>Flat Percent Discounts</li>
    </ul>
  </li>
  <li><strong>Display Customer Savings</strong>: It calculates and displays the total savings for the customer.</li>
  <li><strong>Fetch Live Product Data</strong>: Uses WireMock (mock server on <code>localhost:8081</code>) to fetch live product data.</li>
</ul>

<h2>🛠 Technologies Used</h2>
<ul>
  <li>Java 17</li>
  <li>Spring Boot</li>
  <li>Feign Client</li>
  <li>WireMock</li>
  <li>Maven for dependency management and build</li>
  <li>JUnit for unit testing</li>
</ul>

<h2>📁 Project Structure</h2>
<pre>
src
├── main
│   ├── java
│   │   └── com
│   │       └── grocery
│   │           ├── PaymentSystemApplication.java        # Main Spring Boot application
│   │           ├── controller
│   │           │   ├── CartController.java              # REST controller for handling cart operations
│   │           ├── service
│   │           │   ├── CartService.java                 # Service handling cart and promotions logic
│   │           │   ├── ProductService.java              # Service to fetch product data via Feign
│   │           ├── dto
│   │           │   ├── CartDto.java                     # Data Transfer Object for the cart
│   │           │   ├── ProductDto.java                  # Data Transfer Object for products
│   │           │   ├── PromotionDto.java                # Data Transfer Object for promotions
│   │           ├── feign
│   │           │   ├── ProductFeignClient.java          # Feign client to fetch products
├── test
│   ├── java
│   │   └── com
│   │       └── grocery
│   │           ├── CartServiceTest.java                 # Unit tests for CartService
│   │           ├── ProductServiceTest.java              # Unit tests for ProductService
└── resources
    └── application.properties                           # Application configuration
</pre>

<h2>🚀 Getting Started</h2>

<h3>Prerequisites</h3>
<ul>
  <li>Java 17</li>
  <li>Maven 3.6+</li>
  <li>WireMock (preconfigured in the project)</li>
</ul>

<h3>Clone the Repository</h3>
<pre>
<code>
git clone https://github.com/yourusername/payment-sys.git
cd payment-sys
</code>
</pre>

<h3>Build the Project</h3>
<p>Use Maven to clean, compile, and package the project:</p>
<pre>
<code>
mvn clean install
</code>
</pre>

<h3>Running the Application</h3>
<p>Run the Spring Boot application:</p>
<pre>
<code>
mvn spring-boot:run
</code>
</pre>

<p>This will start the application at <code>http://localhost:8080</code>.</p>

<h3>Running the Mock API (WireMock)</h3>
<p>WireMock is configured to run locally at <code>http://localhost:8081</code>. You can start the WireMock server with your predefined mappings by running:</p>
<pre>
<code>
java -jar wiremock-standalone.jar --port 8081
</code>
</pre>

<p>Ensure that you have the <code>WireMock</code> mappings in the correct folder before starting.</p>

<h2>📜 Endpoints</h2>

<h3>Add Item to Cart</h3>
<ul>
  <li><strong>URL</strong>: <code>POST /cart/addItemToCart</code></li>
  <li><strong>Description</strong>: Adds an item to the cart and applies promotions.</li>
  <li><strong>Request Body</strong>:</li>
</ul>
<pre>
<code>
{
  "items": [
    {
      "productId": "PWWe3w1SDU",
      "quantity": 3
    }
  ]
}
</code>
</pre>

<h3>View Summary (Total Price and Savings)</h3>
<ul>
  <li><strong>URL</strong>: <code>GET /cart/summary</code></li>
  <li><strong>Description</strong>: Returns the total price and savings for the current cart.</li>
</ul>

<h2>🧪 Testing</h2>
<p>Run unit tests:</p>
<pre>
<code>
mvn test
</code>
</pre>

<p>Unit tests cover:</p>
<ul>
  <li><strong>CartService</strong>: Validating the logic for adding items, applying promotions, and calculating savings.</li>
  <li><strong>ProductService</strong>: Ensuring product data is fetched correctly via Feign.</li>
</ul>

<h2>⚙️ Continuous Integration (CI)</h2>
<p>A <strong>GitHub Actions</strong> workflow (<code>.github/workflows/ci.yml</code>) is set up to automatically build and test the project on every push or pull request to the <code>main</code> branch.</p>

<h2>🤝 Contribution</h2>
<ul>
  <li>Fork the repository.</li>
  <li>Create a feature branch (<code>git checkout -b feature-branch</code>).</li>
  <li>Commit your changes (<code>git commit -m "Add some feature"</code>).</li>
  <li>Push to the branch (<code>git push origin feature-branch</code>).</li>
  <li>Create a new Pull Request.</li>
</ul>

<h2>📄 License</h2>
<p>This project is licensed under the MIT License.</p>

<h2>📖 Summary</h2>
<p>This is a simple yet extendable payment system designed for a grocery store. The system can be enhanced to include more features, such as new promotion types, enhanced customer management, and improved inventory handling.</p>

<h2>🐳 Running the Application with Docker</h2>

<p>You can run the entire Spring Boot application within a Docker container by following these steps:</p>

<h3>1. Build the Docker Image</h3>
<p>Ensure you have Docker installed on your machine. From the root directory of the project (where the <code>Dockerfile</code> is located), run the following command to build the Docker image:</p>
<pre>
<code>
docker build -t payment-system .
</code>
</pre>

<h3>2. Run the Docker Container</h3>
<p>After successfully building the image, you can run the container with this command:</p>
<pre>
<code>
docker run -p 8080:8080 payment-system
</code>
</pre>

<p>This command runs the Docker container and exposes the application on <code>localhost:8080</code>. Now, you can access the endpoints:</p>

<ul>
  <li><strong>Add Item to Cart</strong>: <code>POST /cart/addItemToCart</code></li>
  <li><strong>View Summary</strong>: <code>GET /cart/summary</code></li>
</ul>

<h3>3. Stopping the Container</h3>
<p>If you need to stop the Docker container, run:</p>
<pre>
<code>
docker ps        # List running containers and get the container ID
docker stop [container-id]  # Replace [container-id] with your container's ID
</code>
</pre>

<h3>4. Optional: Running in Detached Mode</h3>
<p>If you want to run the container in the background, use the <code>-d</code> flag:</p>
<pre>
<code>
docker run -d -p 8080:8080 payment-system
</code>
</pre>

<p>This will keep the container running in the background, and you can interact with the application as usual on <code>localhost:8080</code>.</p>
