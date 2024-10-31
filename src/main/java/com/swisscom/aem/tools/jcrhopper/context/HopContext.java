package com.swisscom.aem.tools.jcrhopper.context;

import com.swisscom.aem.tools.jcrhopper.HopperException;
import com.swisscom.aem.tools.jcrhopper.config.HopConfig;
import java.io.Writer;
import java.util.Map;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.script.ScriptEngineManager;
import org.slf4j.Logger;

public interface HopContext extends Logger {
	/**
	 * Run the given hops on a new node.
	 * <p>
	 * Useful for running descendant hop pipelines.
	 *
	 * @param node the node to run the pipeline on
	 * @param hops the hop pipeline to run
	 * @throws HopperException     if one of the hops encounters a node it cannot handle and is configured to throw
	 * @throws RepositoryException if an error occurs during JCR manipulation
	 */
	void runHops(Node node, Iterable<HopConfig> hops) throws HopperException, RepositoryException;

	/**
	 * Run the given hops on a new node.
	 * <p>
	 * Useful for running descendant hop pipelines.
	 *
	 * @param node                the node to run the pipeline on
	 * @param hops                the hop pipeline to run
	 * @param additionalVariables additional EL/Script variables to set on the descendant pipeline
	 * @throws HopperException     if one of the hops encounters a node it cannot handle and is configured to throw
	 * @throws RepositoryException if an error occurs during JCR manipulation
	 */
	void runHops(Node node, Iterable<HopConfig> hops, Map<String, Object> additionalVariables) throws HopperException, RepositoryException;

	/**
	 * Evaluates a string as JEXL template.
	 *
	 * @param template a JEXL template string, e.g. “Hello, my name is ${name}”
	 * @return the evaluated template
	 */
	String evaluateTemplate(String template);

	/**
	 * Evaluates a JEXL expression and returns its value.
	 *
	 * @param expression the expression to evaluate, e.g. 1+2
	 * @return the evaluated JEXL expression value
	 */
	Object evaluate(String expression);

	/**
	 * Evaluates a JEXL expression and returns whether it evaluates to true.
	 *
	 * @param expression the expression to evaluate, e.g. a &gt; b
	 * @return whether the JEXL expression evaluates to true (or the string "true")
	 */
	boolean expressionMatches(String expression);

	/**
	 * Runs a given hop config on the given node.
	 * <p>
	 * This is used for special purposes where running a hop must be run in isolation.
	 * It also does not create a nested variable map or put the given node on the variable map.
	 * <p>
	 *
	 * @param hopConfig the hop to run
	 * @throws HopperException     if the hop or one of its descendants encounters a node it cannot handle and is configured to throw
	 * @throws RepositoryException if an error occurs during JCR manipulation
	 */
	void runHop(HopConfig hopConfig) throws HopperException, RepositoryException;

	/**
	 * Print a plain text message.
	 *
	 * @param message the message to print
	 */
	void print(String message);

	/**
	 * @return a writer that can be used to print to the response, the same way calling {@link #print(String)} would
	 */
	Writer getWriter();

	/**
	 * Set an EL/script variable to a specific value.
	 * <p>
	 * The value will be visible in this context and all descendant contexts unless shadowed.
	 *
	 * @param name  the variable name
	 * @param value the variable value
	 */
	void setVariable(String name, Object value);

	/**
	 * @return a read-only view of the currently known variables
	 */
	Map<String, Object> getVariables();

	/**
	 * @return a helper for JCR property manipulation
	 */
	JcrFunctions getJcrFunctions();

	/**
	 * @return the script engine manager for running various script types
	 */
	ScriptEngineManager getScriptEngineManager();
}
