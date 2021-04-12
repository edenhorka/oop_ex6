package oop.ex6.main.scopes;

import oop.ex6.main.commands.Command;
import oop.ex6.main.exceptions.ParseException;
import oop.ex6.main.Parser;
import oop.ex6.main.variables.Variable;
import java.util.ArrayList;
import java.util.Hashtable;

/** This class represents a scope, that is opened with "{" and closed with "} */
public class Scope {

    /** the name of the main (global) scope*/
	private static final String GLOBAL = "global";

    /** the name of this scope. */
    String name;

    /** the direct outer scope of this scope. */
    Scope outerScope;

    /** the line number of this scope opening line. */
    int lineNumber;

    /** line number of the last command of this scope */
	private int lastLine;

	/**
	 * Hash table of all scopes and their names.
	 */
	private Hashtable<String, Scope> innerScopes;

	/**
	 * Hash table of all outer scopes and their names.
	 */
	private Hashtable<String, Scope> outerScopes = new Hashtable<>();

	/**
	 * Hash table of all methods in this scope and their names.
	 */
	private Hashtable<String, Method> methods;

	/**
	 * An array list of if and while scopes that this scope holds.
	 */
	private ArrayList<IfWhile> ifWhiles = new ArrayList<>();

	/**
	 * An array list of the commands that this scope holds.
	 */
	private ArrayList<Command> commands = new ArrayList<>();

	/**
	 * An array list of the variables this scope holds.
	 */
	protected Hashtable<String, Variable> variables = new Hashtable<>();

	/**
	 * a boolean that indicates if this scope is a method.
	 */
	boolean isMethod = false;

	/**
	 * a boolean that indicates if this scope is an "if" condition or a while loop.
	 */
	boolean isIfWhile = false;


	/**
	 * Creates a new scope, inside the given outer scope.
	 * @param outerScope - the outer scope of this scope.
	 */
	public Scope(Scope outerScope) {
		this.outerScope = outerScope;
		this.lineNumber = Parser.getCurrentLineNumber();
		// if this is the global scope:
		if (outerScope == null) {
			name = GLOBAL;
			innerScopes = new Hashtable<>();
			innerScopes.put(name,this);
			methods = new Hashtable<>();
		}
	}

	/**
	 * adds the given method scope to the method table.
	 * @param newMethod- a method scope to add.
	 */
	void addMethod(Method newMethod) {
		methods.put(newMethod.getName(), newMethod);
	}

	/**
	 * adds the given if/while scope to the if/while table.
	 * @param ifWhile if/while scope to add.
	 */
	void addIfWhile(IfWhile ifWhile) {
		ifWhiles.add(ifWhile);
	}

	/**
	 * updates scopes
	 */
	void addScopes(){
		outerScopes.putAll(outerScope.outerScopes); // add all outer scopes of parent scope
		outerScopes.put(outerScope.name, this.outerScope);  // add parent scope
		variables.putAll(outerScope.getVariables()); // add all outer variables
		Parser.getGlobalScope().getInnerScopes().put(this.getName(), this);
	}

	/**
	 * adds a new variable to this scope
	 * @param newVariable - variable to add
	 * @throws ParseException - if there's already a variable with the same name in this scope.
	 */
	public void addVariable(Variable newVariable) throws ParseException {
		String varName = newVariable.getName();

		// check if there's a variable in this scope that already has this name.
		if (variables.containsKey(varName) && !outerScopes.containsValue(variables.get(varName).getScope())){
			throw new ParseException(lineNumber, ParseException.ILLEGAL_VARIABLE_ASSIGNMENT);
		} else {
			variables.put(varName, newVariable);
		}
	}

	/**
	 * add a new command to this scope.
	 * @param newCommand a command to add.
	 */
	public void addCommand(Command newCommand) {
		commands.add(newCommand);
	}

	/**
	 * @return the commands list this scope holds.
	 */
	public ArrayList<Command> getCommands() {
		return commands;
	}

	/**
	 * @return the inner scopes this scope holds.
	 */
	public Hashtable<String, Scope> getInnerScopes() {
		return innerScopes;
	}

	/**
	 * @return the direct outer scope of this scope
	 */
	public Scope getOuterScope() {
		return this.outerScope;
	}

	/**
	 * @return the variables this scope holds.
	 */
	public Hashtable<String, Variable> getVariables() {
		return variables;
	}

	/**
	 * @return true if this scope is a method, else false;
	 */
	public boolean isMethod() {
		return isMethod;
	}

	/**
	 * @return the name of this scope.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the method scopes this scope holds.
	 */
	public Hashtable<String, Method> getMethods() {
		return methods;
	}

	/**
	 * @return the if/while scopes this scope holds.
	 */
	public ArrayList<IfWhile> getIfWhiles() {
		return ifWhiles;
	}

	/**
	 * @return the line number this scope was opened.
	 */
	public int getLineNumber() {
		return lineNumber;
	}

	/**
	 * @return the line number of the last command in this scope
	 */
	public int getLastLine() {
		return lastLine;
	}

	/**
	 * sets the last command line number
	 * @param lastLine - the line number to set
	 */
	public void setLastLine(int lastLine) {
		this.lastLine = lastLine;
	}

}
